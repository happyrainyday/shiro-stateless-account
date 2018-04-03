package com.hzchina.account.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzchina.account.dal.dao.UserAuthorityMapper;
import com.hzchina.account.dal.dao.UserMapper;
import com.hzchina.account.dal.entity.User;
import com.hzchina.account.dal.entity.UserAuthority;
import com.hzchina.account.rest.dto.PasswordDto;
import com.hzchina.account.rest.dto.UserDto;
import com.hzchina.account.security.Principal;
import com.hzchina.account.security.SecurityConstants;
import com.hzchina.account.service.AccountService;
import com.hzchina.account.service.domain.UserDomain;
import com.hzchina.account.service.exception.AccountException;
import com.hzchina.account.service.exception.LoginException;
import com.hzchina.common.rest.domain.ErrorCodeEnum;
import com.hzchina.common.service.config.HttpStatusEnum;
import com.hzchina.common.service.config.RestfulConstant;
import com.hzchina.common.utils.Digests;
import com.hzchina.common.utils.Encodes;
import com.hzchina.common.utils.ShiroSucurityUtils;
import com.hzchina.common.utils.TokenUtils;
import com.hzchina.common.utils.UUIDUtils;

/**
 * @ClassName: AccountService
 * @Description: 个人账号服务
 * @author tjf
 * @date 2016年4月18日下午3:39:35
 * @Version V1.00
 */
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	TokenUtils tokenUtils;
	@Autowired
	UserMapper userMapper;
	@Autowired
	UserAuthorityMapper userAuthorityMapper;

	/**
	 * @Name: login
	 * @Description: 登录服务
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月18日下午3:40:58
	 * @param username
	 * @param password
	 * @Return: String 返回类型
	 */
	public String login(String username, String password) {

		// 获取未拉黑的用户
		User user = userMapper.getUserbyUsername(username);
		if (null != user) {
			byte[] salt = Encodes.decodeHex(user.getSalt());
			byte[] hashPassword = Digests.sha1(password.getBytes(), salt, SecurityConstants.HASH_INTERATIONS);
			String entryptPassword = Encodes.encodeHex(hashPassword);
			if (!entryptPassword.equals(user.getPassword())) {
				throw new AccountException(RestfulConstant.INTERNAL_ERROR_CODE_40000, "用户名或密码错误");
			}
			User updateUser = new User();
			updateUser.setUid(user.getUid());
			updateUser.setLastLoginTime(new Date());
			// 更新登录时间
			int count = userMapper.updateByPrimaryKeySelective(updateUser);
			if (count < 1) {
				throw new AccountException(HttpStatusEnum.HTTP_STATUS_ERROR.getCode(),
						RestfulConstant.INTERNAL_ERROR_CODE_50000, "更新信息失败");
			}

			return this.tokenUtils.generateToken(username, password);
		} else {
			throw new AccountException(RestfulConstant.INTERNAL_ERROR_CODE_40001, "无此用户或者已被拉黑");
		}
	}

	/**
	 * @Name: logout
	 * @Description: 登出操作
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月18日下午3:44:38
	 * @Return: 返回类型
	 */
	public void logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
	}

	/**
	 * @Name: registerUser
	 * @Description: 注册用户信息
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月11日上午10:23:32
	 * @param userDto
	 * @Return: String 返回类型
	 */
	@Transactional(rollbackFor = Exception.class)
	public String registerUser(UserDto userDto) {
		
		// 检验名字是否可用
		User existUser = userMapper.getUserbyUsername(userDto.getUsername());
		if (null != existUser) {
			throw new AccountException(RestfulConstant.INTERNAL_ERROR_CODE_40000, "该用户名已被占用");
		}

		User user = new User();
		user.setUid(UUIDUtils.getUUID());
		// 对密码加密和设置盐
		ShiroSucurityUtils.entryptPassword(user, userDto.getPassword());
		user.setFullName(userDto.getFullName());
		user.setUsername(userDto.getUsername());
		user.setRegisterTime(new Date());
		user.setActivated(true);
		UserAuthority userAuthority = new UserAuthority();
		userAuthority.setUid(user.getUid());
		// 默认设置USER角色
		userAuthority.setAuthorityCd("USER");

		userMapper.insertSelective(user);
		userAuthorityMapper.insertSelective(userAuthority);

		// 注册成功后生成token后，可以直接登录
		return this.tokenUtils.generateToken(userDto.getUsername(), userDto.getPassword());
	}

	/**
	 * @Name: checkUserExist
	 * @Description: 检查用户是否存在
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月13日下午3:50:55
	 * @param username
	 * @Return: boolean 返回类型
	 */
	public boolean checkUserExist(String username) {
		User user = userMapper.getUserbyUsername(username);
		if (null == user) {
			return false;
		}

		return true;
	}

	/**
	 * @Name: getCurrentUser
	 * @Description: 保护界面通过后，继续走下一个过滤器链，获取当前用户
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月18日下午3:52:00
	 * @param username
	 * @Return: UserDomain 返回类型
	 */
	public UserDomain getCurrentUser(String username) {
		UserDomain userDomain = null;
		User user = userMapper.getUserbyUsername(username);
		if (null != user) {
			userDomain = new UserDomain();
			userDomain.setUsername(username);
			userDomain.setLastLoginTime(user.getLastLoginTime().getTime());
			userDomain.setFullName(user.getFullName());
			userDomain.setRegisterTime(user.getRegisterTime().getTime());
			// 获取权限列表信息
			List<String> roleList = new ArrayList<String>();
			List<UserAuthority> list = userAuthorityMapper.selectByUid(user.getUid());
			for (UserAuthority userAuthority : list) {
				roleList.add(userAuthority.getAuthorityCd());
			}
			userDomain.setAuthorities(roleList);
		} else {
			throw new AccountException(HttpStatusEnum.HTTP_STATUS_ERROR.getCode(),
					RestfulConstant.INTERNAL_ERROR_CODE_50000, "未找到用户信息");
		}

		return userDomain;
	}

	/**
	 * @Name: updateUserInfo
	 * @Description: 更新用户信息
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月18日下午3:53:08
	 * @param userDto
	 * @param uid
	 * @Return:  返回类型
	 */
	public void updateUserInfo(UserDto userDto, String uid) {
		User user = new User();
		user.setUid(uid);
		user.setFullName(userDto.getFullName());
		// 更新用户信息
		int count = userMapper.updateByPrimaryKeySelective(user);
		if (count < 1) {
			throw new AccountException(HttpStatusEnum.HTTP_STATUS_ERROR.getCode(),
					RestfulConstant.INTERNAL_ERROR_CODE_50000, "用户不存在");
		}
	}

	/**
	 * @Name: updateUserPassword
	 * @Description: 更新用户密码
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月18日下午3:54:32
	 * @param passwordDto
	 * @param httpServletResponse
	 * @param principal
	 * @Return: 返回类型
	 */
	public void updateUserPassword(PasswordDto passwordDto, HttpServletResponse httpServletResponse, Principal principal) {
		User user = userMapper.getUserbyUsername(principal.getUsername());
		if (null != user) {
			byte[] salt = Encodes.decodeHex(user.getSalt());
			byte[] hashPassword = Digests.sha1(passwordDto.getPassword().getBytes(), salt,
					SecurityConstants.HASH_INTERATIONS);
			String entryptPassword = Encodes.encodeHex(hashPassword);
			if (!entryptPassword.equals(user.getPassword())) {
				throw new AccountException(RestfulConstant.INTERNAL_ERROR_CODE_40000, "旧密码错误");
			}

			User updateUser = new User();
			updateUser.setUid(principal.getUid());
			ShiroSucurityUtils.entryptPassword(updateUser, passwordDto.getNewPassword());
			int count = userMapper.updateByPrimaryKeySelective(updateUser);
			if (count < 1) {
				throw new AccountException(HttpStatusEnum.HTTP_STATUS_ERROR.getCode(),
						RestfulConstant.INTERNAL_ERROR_CODE_50000, "更新密码失败");
			}
		} else {
			throw new LoginException(ErrorCodeEnum.ERROR_NOT_LOGIN);
		}

		// 更新密码生成新的token
		String accessToken = this.tokenUtils.generateToken(principal.getUsername(), passwordDto.getPassword());
		httpServletResponse.setContentType("application/json");
		httpServletResponse.setHeader(SecurityConstants.HEADER, accessToken);
	}
}

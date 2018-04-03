package com.hzchina.account.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzchina.account.dal.dao.AuthorityMapper;
import com.hzchina.account.dal.dao.UserAuthorityMapper;
import com.hzchina.account.dal.dao.UserMapper;
import com.hzchina.account.dal.entity.Authority;
import com.hzchina.account.dal.entity.User;
import com.hzchina.account.dal.entity.UserAuthority;
import com.hzchina.account.rest.dto.PasswordDto;
import com.hzchina.account.rest.dto.UserDto;
import com.hzchina.account.service.UserService;
import com.hzchina.account.service.domain.RoleDomain;
import com.hzchina.account.service.domain.UserDomain;
import com.hzchina.account.service.exception.AccountException;
import com.hzchina.common.rest.domain.PageInfoDomain;
import com.hzchina.common.service.config.HttpStatusEnum;
import com.hzchina.common.service.config.RestfulConstant;
import com.hzchina.common.utils.ShiroSucurityUtils;
import com.hzchina.common.utils.TokenUtils;
import com.hzchina.common.utils.UUIDUtils;

/**
 * @ClassName: UserService
 * @Description: 用户管理服务
 * @author tjf
 * @date 2016年4月18日下午3:56:31
 * @Version V1.00
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	TokenUtils tokenUtils;
	@Autowired
	UserMapper userMapper;
	@Autowired
	UserAuthorityMapper userAuthorityMapper;
	@Autowired
	AuthorityMapper authorityMapper;

	/**
	 * @Name: getUserList
	 * @Description: 获取用户列表
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月8日下午5:25:29
	 * @param pageIndex
	 * @param pageSize
	 * @param username
	 * @param fullName
	 * @param activated
	 * @Return: Map<String, Object> 返回类型
	 */
	public Map<String, Object> getUserList(String pageIndex, String pageSize, String username, String fullName,
			Boolean activated) {
		PageInfoDomain pageInfo = new PageInfoDomain();
		UserDomain userDomain = null;
		Map<String, Object> map = new HashMap<String, Object>();

		int index = StringUtils.isBlank(pageIndex) ? 1 : Integer.parseInt(pageIndex);
		int size = StringUtils.isBlank(pageSize) ? 10 : Integer.parseInt(pageSize);

		HashMap<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("username", username);
		conditions.put("fullName", fullName);
		conditions.put("pageIndex", (index - 1) * size);
		conditions.put("pageSize", size);
		if (null != activated) {
			conditions.put("activated", activated);
		}

		long total = 0;
		List<UserDomain> userDomainList = new ArrayList<UserDomain>();
		// 获取总条数
		total = userMapper.selectAllUserCount(conditions);
		List<User> userList = userMapper.getUserList(conditions);
		for (User user : userList) {
			userDomain = new UserDomain();
			userDomain.setUid(user.getUid());
			userDomain.setUsername(user.getUsername());
			userDomain.setFullName(user.getFullName());
			if (user.getLastLoginTime() != null) {
				userDomain.setLastLoginTime(user.getLastLoginTime().getTime());
			}
			userDomain.setRegisterTime(user.getRegisterTime().getTime());
			userDomain.setActivated(user.getActivated());
			List<String> roleList = new ArrayList<String>();
			List<UserAuthority> list = userAuthorityMapper.selectByUid(user.getUid());
			for (UserAuthority userAuthority : list) {
				roleList.add(userAuthority.getAuthorityCd());
			}
			userDomain.setAuthorities(roleList);
			userDomainList.add(userDomain);
		}

		pageInfo.setPageIndex(index);
		pageInfo.setPageSize(size);
		pageInfo.setTotal((int) total);
		map.put("pageInfo", pageInfo);
		map.put("userList", userDomainList);

		return map;
	}

	/**
	 * @Name: getUserDetail
	 * @Description: 获取用户详情
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月18日下午4:10:04
	 * @param uid
	 * @Return: UserDomain 返回类型
	 */
	public UserDomain getUserDetail(String uid) {
		UserDomain userDomain = null;
		User user = userMapper.selectByAdmin(uid);
		if (null != user) {
			userDomain = new UserDomain();
			userDomain.setUid(user.getUid());
			userDomain.setUsername(user.getUsername());
			userDomain.setFullName(user.getFullName());
			if (user.getLastLoginTime() != null) {
				userDomain.setLastLoginTime(user.getLastLoginTime().getTime());
			}
			userDomain.setRegisterTime(user.getRegisterTime().getTime());
			userDomain.setActivated(user.getActivated());
			// 获取权限列表信息
			List<String> roleList = new ArrayList<String>();
			List<UserAuthority> list = userAuthorityMapper.selectByUid(user.getUid());
			for (UserAuthority userAuthority : list) {
				roleList.add(userAuthority.getAuthorityCd());
			}
			userDomain.setAuthorities(roleList);
		} else {
			throw new AccountException(HttpStatusEnum.HTTP_STATUS_ERROR.getCode(),
					RestfulConstant.INTERNAL_ERROR_CODE_50000, "不存在该用户");
		}

		return userDomain;
	}

	/**
	 * @Name: addUser
	 * @Description: 添加用户
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月18日下午4:10:59
	 * @param userDto
	 * @Return: 返回类型
	 */
	@Transactional(rollbackFor = Exception.class)
	public void addUser(UserDto userDto) {
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

		userMapper.insert(user);
		List<String> authorList = userDto.getAuthorities();
		if (null != authorList && 0 < authorList.size()) {
			for (String author : authorList) {
				UserAuthority userAuthority = new UserAuthority();
				userAuthority.setUid(user.getUid());
				userAuthority.setAuthorityCd(author);
				userAuthorityMapper.insertSelective(userAuthority);
			}
		} else {
			UserAuthority userAuthority = new UserAuthority();
			userAuthority.setUid(user.getUid());
			userAuthority.setAuthorityCd("USER");
			userAuthorityMapper.insertSelective(userAuthority);
		}
	}

	/**
	 * @Name: updateUser
	 * @Description: 修改用户信息
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月11日上午9:29:36
	 * @param uid
	 * @param userDto
	 * @Return: 返回类型
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateUser(String uid, UserDto userDto) {
		Boolean isActivated = false;
		isActivated = Boolean.valueOf(userDto.getActivated());
		User user = new User();
		user.setUid(uid);
		user.setFullName(userDto.getFullName());
		// 用于表示拉黑/解封用户
		user.setActivated(isActivated);
		int updateCount = userMapper.updateByPrimaryKeySelective(user);
		if (updateCount < 1) {
			throw new AccountException(HttpStatusEnum.HTTP_STATUS_ERROR.getCode(),
					RestfulConstant.INTERNAL_ERROR_CODE_50000, "用户信息更新失败");
		}
		// 对于权限的的更新，先删除后添加
		int deleteCount = userAuthorityMapper.deleteByUid(uid);
		if (deleteCount < 1) {
			throw new AccountException(HttpStatusEnum.HTTP_STATUS_ERROR.getCode(),
					RestfulConstant.INTERNAL_ERROR_CODE_50001, "用户权限更新失败");
		}
		List<String> authorList = userDto.getAuthorities();
		if (null != authorList && 0 < authorList.size()) {
			for (String author : authorList) {
				UserAuthority userAuthority = new UserAuthority();
				userAuthority.setUid(user.getUid());
				userAuthority.setAuthorityCd(author);
				userAuthorityMapper.insertSelective(userAuthority);
			}
		} else {
			UserAuthority userAuthority = new UserAuthority();
			userAuthority.setUid(user.getUid());
			userAuthority.setAuthorityCd("USER");
			userAuthorityMapper.insertSelective(userAuthority);
		}
	}

	/**
	 * @Name: changePassword
	 * @Description: 管理员修改密码
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月8日下午5:27:23
	 * @param uid
	 * @param passwordDto
	 * @Return: void 返回类型
	 */
	public void changePassword(String uid, PasswordDto passwordDto) {
		User user = new User();
		user.setUid(uid);
		ShiroSucurityUtils.entryptPassword(user, passwordDto.getNewPassword());
		int count = userMapper.updateByPrimaryKeySelective(user);
		if (count < 1) {
			throw new AccountException(HttpStatusEnum.HTTP_STATUS_ERROR.getCode(),
					RestfulConstant.INTERNAL_ERROR_CODE_50000, "密码修改失败");
		}
	}

	/**
	 * @Name: blackOrWhiteUser
	 * @Description: 拉黑或拉白用户
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月18日下午4:16:33
	 * @param uid
	 * @param status
	 * @Return: void 返回类型
	 */
	public void blackOrWhiteUser(String uid, Integer status) {
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		// 1表示拉白，0表示拉黑
		conditions.put("activated", status);
		conditions.put("uid", uid);
		int count = userMapper.blackOrWhiteUser(conditions);
		if (count < 1) {
			throw new AccountException(HttpStatusEnum.HTTP_STATUS_ERROR.getCode(),
					RestfulConstant.INTERNAL_ERROR_CODE_50000, "拉黑或者拉白未成功");
		}
	}

	/**
	 * @Name: getRoleList
	 * @Description: 管理员获取权限信息
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月8日下午5:35:41
	 * @Return: List<RoleDomain> 返回类型
	 */
	public List<RoleDomain> getRoleList() {
		List<RoleDomain> roleList = new ArrayList<RoleDomain>();
		RoleDomain roleVo = null;
		List<Authority> list = authorityMapper.getAuthorityList();
		for (Authority authority : list) {
			roleVo = new RoleDomain();
			roleVo.setAuthorityCd(authority.getAuthorityCd());
			roleVo.setAuthorityName(authority.getAuthorityName());
			roleList.add(roleVo);
		}

		return roleList;
	}

	/**
	 * @Title: getUserbyUsername
	 * @Description: 用户名查找用户
	 * @param username
	 * @return 返回类型
	 * @throws
	 */
	@Override
	public User getUserbyUsername(String username) {
		return userMapper.getUserbyUsername(username);
	}

	/**
	 * @Name: getUserByid
	 * @Description:TODO
	 * @Author: hzzjh
	 * @Version: V1.00
	 * @CreateDate: 下午2:22:38
	 **/
	@Override
	public User getUserByid(String id) {
		return userMapper.selectByIdNotActivated(id);
	}

}

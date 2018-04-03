package com.hzchina.account.service;

import javax.servlet.http.HttpServletResponse;

import com.hzchina.account.rest.dto.PasswordDto;
import com.hzchina.account.rest.dto.UserDto;
import com.hzchina.account.security.Principal;
import com.hzchina.account.service.domain.UserDomain;

/**
 * @ClassName: AccountService
 * @Description: 个人账号服务
 * @author tjf
 * @date 2016年4月18日下午3:39:35
 * @Version V1.00
 */
public interface AccountService {

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
	public String login(String username, String password);

	/**
	 * @Name: logout
	 * @Description: 登出操作
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月18日下午3:44:38
	 * @Return: void 返回类型
	 */
	public void logout();
	
	/**
	 * @Name: registerUser
	 * @Description: 注册用户信息
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月11日上午10:23:32
	 * @param userDto
	 * @Return: String 返回类型
	 */
	public String registerUser(UserDto userDto);

	/**
	 * @Name: checkUserExist
	 * @Description: 检查用户是否存在 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月13日下午3:50:55
	 * @param username
	 * @Return: boolean 返回类型
	 */
	public boolean checkUserExist(String username);

	/**
	 * @Name: getCurrentUser
	 * @Description: 保护界面通过后，继续走下一个过滤器链，获取当前用户
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月18日下午3:52:00
	 * @param username
	 * @Return: UserDomain 返回类型
	 */
	public UserDomain getCurrentUser(String username);
	
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
	public void updateUserInfo(UserDto userDto, String uid);
	
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
	public void updateUserPassword(PasswordDto passwordDto, HttpServletResponse httpServletResponse, Principal principal);
}

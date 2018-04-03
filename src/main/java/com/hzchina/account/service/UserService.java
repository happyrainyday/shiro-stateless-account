package com.hzchina.account.service;

import java.util.List;
import java.util.Map;

import com.hzchina.account.dal.entity.User;
import com.hzchina.account.rest.dto.PasswordDto;
import com.hzchina.account.rest.dto.UserDto;
import com.hzchina.account.service.domain.RoleDomain;
import com.hzchina.account.service.domain.UserDomain;

/**
 * @ClassName: UserService
 * @Description: 用户管理服务
 * @author tjf
 * @date 2016年4月18日下午3:56:31
 * @Version V1.00
 */
public interface UserService {

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
			Boolean activated);

	/**
	 * @Name: getUserDetail
	 * @Description: 获取用户详情
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月18日下午4:10:04
	 * @param uid
	 * @Return: UserDomain 返回类型
	 */
	public UserDomain getUserDetail(String uid);
	/**
	 * @Name: addUser
	 * @Description: 添加用户
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月18日下午4:10:59
	 * @param userDto
	 * @Return: 返回类型
	 */
	public void addUser(UserDto userDto);

	/**
	 * @Name: updateUser
	 * @Description: 修改用户信息
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月11日上午9:29:36
	 * @param uid
	 * @param userDto
	 * @Return:  返回类型
	 */
	public void updateUser(String uid, UserDto userDto);

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
	public void changePassword(String uid, PasswordDto passwordDto);

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
	public void blackOrWhiteUser(String uid, Integer status);

	/**
	 * @Name: getRoleList
	 * @Description: 管理员获取权限信息
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月8日下午5:35:41
	 * @Return: List<RoleDomain> 返回类型
	 */
	public List<RoleDomain> getRoleList();
	
	/**
	* @Title: getUserbyUsername
	* @Description: 用户名查找用户
	* @param @param username
	* @param @return    参数
	* @return User    返回类型
	* @throws
	*/
	public User getUserbyUsername(String username);
	
	public User getUserByid(String id);
}

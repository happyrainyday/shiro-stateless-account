package com.hzchina.common.utils;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.hzchina.account.dal.entity.User;
import com.hzchina.account.security.Principal;
import com.hzchina.account.security.SecurityConstants;

/**
 * @ClassName: ShiroSucurityUtils
 * @Description: 安全加密工具
 * @author tjf
 * @date 2016年9月22日下午3:59:45
 * @Version V1.00
 */
public class ShiroSucurityUtils {

	/**
	 * @Name: entryptPassword
	 * @Description: 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月18日下午3:55:42
	 * @param user
	 * @param plainPassword
	 * @Return: void 返回类型
	 */
	public static void entryptPassword(User user, String plainPassword) {
		byte[] salt = Digests.generateSalt(SecurityConstants.SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, SecurityConstants.HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * @Name: getPrincipal
	 * @Description: 获取认证信息，包括权限信息
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年9月28日上午11:18:11
	 * @Return: Principal 返回类型
	 */
	public static Principal getPrincipal(){
		Subject subject = SecurityUtils.getSubject();
		return (Principal) subject.getPrincipal();
	}
	
	public static boolean checkAuthoriteNotAllow(String authorite){
		List<String> authorites=getPrincipal().getAuthorites();
		boolean flag=true;
		for(String str:authorites){
			if(str.equals(authorite)){
				flag=false;
			}
		}
		return flag;
	}
}

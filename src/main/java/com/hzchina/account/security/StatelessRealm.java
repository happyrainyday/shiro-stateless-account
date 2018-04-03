package com.hzchina.account.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.hzchina.account.dal.dao.UserAuthorityMapper;
import com.hzchina.account.dal.dao.UserMapper;
import com.hzchina.account.dal.entity.User;
import com.hzchina.account.dal.entity.UserAuthority;
import com.hzchina.common.utils.Encodes;

/**
 * @ClassName: StatelessRealm
 * @Description: 无状态处理
 * @author tjf
 * @date 2016年4月8日下午3:27:10
 * @Version V1.00
 */
public class StatelessRealm extends AuthorizingRealm {

	@Autowired
	UserMapper userMapper;
	@Autowired
	UserAuthorityMapper userAuthorityMapper;

	@Override
	public boolean supports(AuthenticationToken token) {
		// 仅支持StatelessToken类型的Token
		return token instanceof StatelessToken;
	}

	/**
	 * @Name: doGetAuthorizationInfo
	 * @Description: 授权中心
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月8日下午3:29:19
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 根据用户名查找角色，请根据需求实现
		Principal principal = (Principal) principals.getPrimaryPrincipal();
		try {
			if (null != principal) {
				// 获取权限
				SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
				List<String> list = new ArrayList<String>();
				List<UserAuthority> authorityList = userAuthorityMapper.selectByUid(principal.getUid());
				for (UserAuthority userAuthority : authorityList) {
					list.add(userAuthority.getAuthorityCd());
				}
				authorizationInfo.addRoles(list);
				// 在认证中心添加
				// principal.setAuthorites(list); 
				return authorizationInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * @Name: doGetAuthenticationInfo
	 * @Description: 认证中心
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月8日下午3:30:09
	 * @param token
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		StatelessToken statelessToken = (StatelessToken) token;
		String username = statelessToken.getUsername();

		try {
			User user = userMapper.getUserbyUsername(username);
			if (null != user) {
				SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
				List<String> list = new ArrayList<String>();
				List<UserAuthority> authorityList = userAuthorityMapper.selectByUid(user.getUid());
				for (UserAuthority userAuthority : authorityList) {
					list.add(userAuthority.getAuthorityCd());
				}
				authorizationInfo.addRoles(list);
				Principal principal = new Principal(user);
				principal.setAuthorites(list);
				byte[] salt = Encodes.decodeHex(user.getSalt());
				return new SimpleAuthenticationInfo(principal, user.getPassword(), ByteSource.Util.bytes(salt),
						getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 设定密码校验的Hash算法与迭代次数
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(SecurityConstants.HASH_ALGORITHM);
		matcher.setHashIterations(SecurityConstants.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}

}
/**
 * @Title: AnyRolesAuthorFilter.java
 * @Package com.tairanchina.account.secuity
 * @Description: TODO(用一句话描述该文件做什么)
 * @author tjf
 * @date 2016年4月15日
 * @version V1.0
 */
package com.hzchina.account.security;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

/**
 * @ClassName: AnyRolesAuthorFilter
 * @Description: 任何角色的过滤器
 * @author tjf
 * @date 2016年4月15日下午5:06:07
 * @Version V1.00
 */
public class AnyRolesAuthorFilter extends RolesAuthorizationFilter {

	/**
	 * @Name: isAccessAllowed
	 * @Description: 判断是否有任意权限 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月15日下午5:06:07
	 * @param request
	 * @param response
	 * @param mappedValue
	 * @throws IOException
	 */
	@Override
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws IOException {
		String[] roles = (String[]) mappedValue;
		if (roles == null || roles.length == 0) {
			return true; // 如果没有设置角色参数，默认成功
		}
		
		for (String role : roles) {
			if (getSubject(request, response).hasRole(role)) {
				return true;
			}
		}
		
		return false; // 跳到onAccessDenied处理
	}

	/**
	 * @Name: onAccessDenied
	 * @Description: 用于角色验证不通过后的处理,如果是有状态则进行原始操作（jsp），若无状态进行restful操作
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年9月22日下午3:14:14
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		String message = null;
		boolean sessionCreate = true;
		
		Subject subject = getSubject(request, response);
		try {
			if (null == subject.getSession()) {
				sessionCreate = false;
			}
		} catch (Exception e) {
			sessionCreate = false;
		}

		if (sessionCreate) {
			super.onAccessDenied(request, response);
		} else {
			if (subject.getPrincipal() == null) {
				message = "{\"code\":\"401\",\"message\":\"please login\"}";
				onLoginFail(request, response, message);
			} else {
				message = "{\"code\":\"403\",\"message\":\"Unauthorized\"}";
				onLoginFail(request, response, message);
			}
		}

		return false;
	}

	/**
	 * @Name: onLoginFail
	 * @Description: 处理登录失败
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月8日下午5:50:45
	 * @param request
	 * @param response
	 * @param message
	 * @throws IOException
	 * @Return: void 返回类型
	 */
	private void onLoginFail(ServletRequest request, ServletResponse response, String message) throws IOException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
		httpResponse.setContentType("application/json");
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.getWriter().write(message);
	}

}

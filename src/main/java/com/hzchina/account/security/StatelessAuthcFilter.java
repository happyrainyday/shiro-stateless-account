package com.hzchina.account.security;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.hzchina.common.utils.TokenUtils;

/**
 * @ClassName: StatelessAuthcFilter
 * @Description: 认证过滤器
 * @author tjf
 * @date 2016年4月18日下午3:20:46
 * @Version V1.00
 */
public class StatelessAuthcFilter extends AccessControlFilter {

	@Autowired
	TokenUtils tokenUtils;

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		return false;
	}

	/**
	 * @Name: onAccessDenied
	 * @Description: 用于处理无状态的认证请求
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月8日上午9:27:12
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		String message = "";
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		 // 默认从头部带入token
		String authToken = httpRequest.getHeader(SecurityConstants.HEADER);
		if (StringUtils.isBlank(authToken)) {
			message = "{\"code\":\"401\",\"message\":\"Unauthorized, please login\"}";
			onLoginFail(request, response, message);
			return false;
		}

		// 首先检查token是否有效
		if (!this.tokenUtils.validateToken(authToken)) {
			message = "{\"code\":\"401\",\"message\":\"token invalid\"}";
			onLoginFail(request, response, message);
			return false;
		}
        
		// 从token中提取用户名和密码
		String username = this.tokenUtils.getUsernameFromToken(authToken);
		String password = this.tokenUtils.getPasswordFromToken(authToken);
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			message = "{\"code\":\"401\",\"message\":\"token invalid\"}";
			onLoginFail(request, response, message);
			return false;
		}

		StatelessToken token = new StatelessToken(username, password);
		try {
			// 登录验证，具体逻辑由realm处理
			getSubject(request, response).login(token);
		} catch (Exception e) {
			e.printStackTrace();
			// 登录失败，跳转到登录页
			message = "{\"code\":\"401\",\"message\":\"Unauthorized, please login\"}";
			onLoginFail(request, response, message);
			return false;
		}

		// 判断是否刷新token
		if (this.tokenUtils.canTokenBeRefreshed(authToken)) {
			String refreshToken = this.tokenUtils.refreshToken(authToken);
			if (null != refreshToken) {
				// 此时刷新的token可以带回
				httpResponse.setContentType("application/json");
				httpResponse.setHeader(SecurityConstants.HEADER, refreshToken);
			}
		}

		return true;
	}

	/**
	 * @Name: onLoginFail
	 * @Description: 失败处理信息 
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月8日下午3:24:16
	 * @param request
	 * @param response
	 * @param message
	 * @throws IOException
	 * @Return: void 返回类型
	 */
	private void onLoginFail(ServletRequest request, ServletResponse response, String message) throws IOException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		httpResponse.setContentType("application/json");
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.getWriter().write(message);
	}

}

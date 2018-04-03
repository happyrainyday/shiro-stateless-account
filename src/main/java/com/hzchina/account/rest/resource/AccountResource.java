package com.hzchina.account.rest.resource;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hzchina.account.rest.dto.PasswordDto;
import com.hzchina.account.rest.dto.UserDto;
import com.hzchina.account.security.Principal;
import com.hzchina.account.service.AccountService;
import com.hzchina.account.service.exception.LoginException;
import com.hzchina.account.utils.AccountUtils;
import com.hzchina.common.rest.domain.BaseResult;
import com.hzchina.common.rest.domain.ErrorCodeEnum;
import com.hzchina.common.service.config.HttpStatusEnum;
import com.hzchina.common.service.exception.IllegalArgumentException;
import com.hzchina.common.utils.ShiroSucurityUtils;

@Path("admin/account")
public class AccountResource {

	@Autowired
	AccountService accountService;

	@Path("/login")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResult<?> login(@FormParam("username") String username, @FormParam("password") String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 参数异常
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			throw new IllegalArgumentException(HttpStatusEnum.HTTP_STATUS_PARAM_ERROR.getCode(),
					ErrorCodeEnum.PARAMETERS_IS_NULL_2, "username", "password");
		}
		
		map.put("token", accountService.login(username, password));

		return new BaseResult<>(map);
	}

	@Path("/register")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResult<?> register(UserDto userDto) {
		Map<String, Object> map = new HashMap<String, Object>();

		// 参数校验
		if (StringUtils.isBlank(userDto.getUsername()) || StringUtils.isBlank(userDto.getPassword())
				|| StringUtils.isBlank(userDto.getFullName())) {
			throw new IllegalArgumentException(ErrorCodeEnum.ERROR_PARAMETERS);
		}

		map.put("token", accountService.registerUser(userDto));

		return new BaseResult<>(map);
	}

	@Path("/logout")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResult<?> logout() {
		accountService.logout();
		return new BaseResult<>();
	}

	@Path("/exist")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResult<?> exist(@QueryParam("username") String username) {
		// 参数校验
		if (StringUtils.isBlank(username)) {
			throw new IllegalArgumentException(ErrorCodeEnum.ERROR_PARAMETERS);
		}

		return new BaseResult<>(accountService.checkUserExist(username));
	}

	// 获取当前用户
	@Path("/user/me")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResult<?> getCurrentUser() {
		// 获取当前登录用户信息
		Principal principal = ShiroSucurityUtils.getPrincipal();
		if (null == principal){
			throw new LoginException(ErrorCodeEnum.ERROR_NOT_LOGIN);
		}
		
		return new BaseResult<>(accountService.getCurrentUser(principal.getUsername()));
	}

	// 更新用户信息
	@PUT
	@Path("/user")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public BaseResult<?> updateUserInfo(UserDto userDto) {
		
		// 參數校驗
		if (!AccountUtils.checkUserDto(userDto)) {
			throw new IllegalArgumentException(ErrorCodeEnum.ERROR_PARAMETERS);
		}
		
		// 获取当前登录用户信息
		Principal principal = ShiroSucurityUtils.getPrincipal();
		if (null == principal) {
			throw new LoginException(ErrorCodeEnum.ERROR_NOT_LOGIN);
		}
		
		accountService.updateUserInfo(userDto, principal.getUid());
		
		return new BaseResult<>();
	}

	// 更新密码
	@PUT
	@Path("/user/password")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public BaseResult<?> updateUserPassword(PasswordDto passwordDto, @Context HttpServletResponse httpServletResponse) {
		// 参数校验
		if (StringUtils.isBlank(passwordDto.getPassword()) || StringUtils.isBlank(passwordDto.getNewPassword())) {
			throw new IllegalArgumentException(ErrorCodeEnum.ERROR_PARAMETERS);
		}
		
		// 获取当前登录用户信息
		Principal principal = ShiroSucurityUtils.getPrincipal();
		if (null == principal) {
			throw new LoginException(ErrorCodeEnum.ERROR_NOT_LOGIN);
		}

		accountService.updateUserPassword(passwordDto, httpServletResponse, principal);

		return new BaseResult<>();
	}
}

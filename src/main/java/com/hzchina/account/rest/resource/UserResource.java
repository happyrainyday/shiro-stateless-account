package com.hzchina.account.rest.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.hzchina.account.rest.dto.PasswordDto;
import com.hzchina.account.rest.dto.UserDto;
import com.hzchina.account.service.UserService;
import com.hzchina.account.utils.AccountUtils;
import com.hzchina.common.rest.domain.BaseResult;
import com.hzchina.common.rest.domain.ErrorCodeEnum;
import com.hzchina.common.service.exception.IllegalArgumentException;
import com.hzchina.common.utils.StringUtils;

@Path("admin/account")
public class UserResource {

	@Autowired
	UserService userService;

	// 获取列表
	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResult<?> getUserList(@QueryParam("pageIndex") String pageIndex, @QueryParam("pageSize") String pageSize,
			@QueryParam("username") String username, @QueryParam("fullName") String fullName,
			@QueryParam("activated") Boolean activated) {
		
		// 参数校验
		if (StringUtils.isNotBlank(pageSize) && !StringUtils.isInteger(pageSize)) {
			throw new IllegalArgumentException(ErrorCodeEnum.ERROR_PARAMETERS);
		}

		if (StringUtils.isNotBlank(pageIndex) && !StringUtils.isInteger(pageIndex)) {
			throw new IllegalArgumentException(ErrorCodeEnum.ERROR_PARAMETERS);
		}
		
		return new BaseResult<>(userService.getUserList(pageIndex, pageSize, username, fullName, activated));
	}

	// 获取详情
	@GET
	@Path("/users/{uid}")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResult<?> getuser(@PathParam("uid") String uid) {
		return new BaseResult<>(userService.getUserDetail(uid));
	}

	// 新建
	@POST
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public BaseResult<?> addUser(UserDto userDto) {
		// 参数校验
		if (StringUtils.isBlank(userDto.getUsername()) || StringUtils.isBlank(userDto.getPassword())
				|| StringUtils.isBlank(userDto.getFullName())) {
			throw new IllegalArgumentException(ErrorCodeEnum.ERROR_PARAMETERS);
		}

		userService.addUser(userDto);

		return new BaseResult<>();
	}

	// 更新
	@PUT
	@Path("/users/{uid}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public BaseResult<?> updateUser(@PathParam("uid") String uid, UserDto userDto) {
		// 为了安全，前后端都对参数进行有效性检验
		if (!AccountUtils.checkUserDto(userDto)) {
			throw new IllegalArgumentException(ErrorCodeEnum.ERROR_PARAMETERS);
		}

		if (!"true".equalsIgnoreCase(userDto.getActivated()) && !"false".equalsIgnoreCase(userDto.getActivated())) {
			throw new IllegalArgumentException(ErrorCodeEnum.ERROR_PARAMETERS);
		}

		userService.updateUser(uid, userDto);

		return new BaseResult<>();
	}

	// 修改密码
	@PUT
	@Path("/users/{uid}/password")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public BaseResult<?> changePassword(@PathParam("uid") String uid, PasswordDto passwordDto) {
		// 参数校验
		if (StringUtils.isBlank(passwordDto.getNewPassword())) {
			throw new IllegalArgumentException(ErrorCodeEnum.ERROR_PARAMETERS);
		}
		
		userService.changePassword(uid, passwordDto);

		return new BaseResult<>();
	}

	// 更新用户状态, 拉黑或者拉白
	@PUT
	@Path("/users/{uid}/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResult<?> blackOrWhiteUser(@PathParam("uid") String uid, @PathParam("status") String status) {
		// 参数校验
		if (!StringUtils.isInteger(status)) {
			throw new IllegalArgumentException(ErrorCodeEnum.ERROR_PARAMETERS);
		}
		
		userService.blackOrWhiteUser(uid, Integer.parseInt(status));
		
		return new BaseResult<>();
	}

	// 获取权限列表
	@GET
	@Path("/authorities")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResult<?> getRoleList() {
		return new BaseResult<>(userService.getRoleList());
	}
}

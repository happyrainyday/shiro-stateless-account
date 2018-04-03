package com.hzchina.common.service.exception.mapper;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.dao.DataAccessException;

import com.hzchina.account.service.exception.AccountException;
import com.hzchina.account.service.exception.LoginException;
import com.hzchina.common.rest.domain.BaseResult;
import com.hzchina.common.rest.domain.ResultInfo;
import com.hzchina.common.service.config.HttpStatusEnum;
import com.hzchina.common.service.config.RestfulConstant;
import com.hzchina.common.service.exception.IllegalArgumentException;
import com.hzchina.common.utils.JSONUtils;

/**
 * @ClassName: SystemExceptionMapper
 * @Description: 统一处理本系统的异常处理类， 一般分为三大类型异常，1、参数型异常 400 2、账号型异常 401 403
 *               3、业务型异常  200 400 500  4、数据型异常 500
 * @author tjf
 * @date 2016年11月7日上午9:37:30
 */
@Provider
public class SystemExceptionMapper implements ExceptionMapper<Exception>{

	/**
	 * @Name: toResponse
	 * @Description: 处理异常
	 * @Author: tjf
	 * @CreateDate: 2016年11月7日上午9:38:27
	 * @param exception
	 */
	@Override
	public Response toResponse(Exception exception) {
	    BaseResult<?> result = new BaseResult<>();
	    int status = HttpStatusEnum.HTTP_STATUS_ERROR.getCode();
	    boolean isFlag = false;
	    String message = RestfulConstant.SYSTEM_ERROR;
	    String code = RestfulConstant.INTERNAL_ERROR_CODE_50000;
	    
		if (exception instanceof IllegalArgumentException) { // 参数异常， 以40001开始

		} else if (exception instanceof AccountException) { // 业务异常，具体自定义，一般不会展示具体的异常

		} else if (exception instanceof LoginException) { // 登录异常， 401，403

		} else if (exception instanceof NotAllowedException) { // 405 
			status = HttpStatusEnum.HTTP_STATUS_NOT_METHOD.getCode();
			code = RestfulConstant.RESULT_STATUS_NOT_METHOD;
			message = RestfulConstant.RESULT_MESSAGE_ERROR_NOT_METHOD;
			isFlag = true;
		} else if (exception instanceof NotFoundException) { // 404
			status = HttpStatusEnum.HTTP_STATUS_NOT_FOUND.getCode();
			code = RestfulConstant.RESULT_STATUS_NOT_FOUND;
			message = RestfulConstant.RESULT_MESSAGE_ERROR_NOT_FOUND;
			isFlag = true;
		} else if (exception instanceof DataAccessException) { // 操作数据库发生的异常
			// 服务异常
			message = RestfulConstant.SERVICE_ERROR;
			isFlag = true;
		} else { // 统一异常
			// 系统异常
			isFlag = true;
			message = RestfulConstant.SYSTEM_ERROR;
			code = RestfulConstant.INTERNAL_ERROR_CODE_50001;
		}
		
		if (isFlag) {
			result.setCode(code);
	    	result.setMessage(message);
	    	return Response.status(status).entity(result).build();
		}
		
	    ResultInfo resultInfo = JSONUtils.toBean(exception.getMessage(), ResultInfo.class);
	    if (null == resultInfo) {
	    	result.setCode(code);
	    	result.setMessage(message);
	    	return Response.status(status).entity(result).build();
		}
	    
	    status = resultInfo.getHttpCode();
	    message = resultInfo.getMessage();
	    code = resultInfo.getCode();

		result.setCode(code);
		result.setMessage(message);
		return Response.status(status).entity(result).build();
	}

}

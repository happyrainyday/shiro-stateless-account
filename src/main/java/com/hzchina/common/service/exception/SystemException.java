package com.hzchina.common.service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hzchina.common.rest.domain.ErrorCodeEnum;
import com.hzchina.common.rest.domain.ResultInfo;
import com.hzchina.common.utils.JSONUtils;
import com.hzchina.common.utils.StringUtils;

/**
 * @ClassName: SystemException
 * @Description: 系统共用的运行时异常
 * @author tjf
 * @date 2016年11月7日下午2:18:37
 */
public class SystemException extends RuntimeException {
	
	private static final long serialVersionUID = 8705571580755677233L;

	protected Logger logger = LoggerFactory.getLogger(SystemException.class);

	private String message;

	private String showMessage;
	
	private ResultInfo resultInfo = new ResultInfo();

	public SystemException() {
		super();
	}

	public SystemException(String message) {
		super(message);
		this.message = message;
		logger.error(message);
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemException(Throwable cause) {
		super(cause);
	}

	public SystemException(String format, Object... params) {
		message = format;
		format = format.replace("{}", "%S");
		message = message.replace("{}", "");
		// 用于打印日志信息
		showMessage = String.format(format, params);
		// 用于前端提示信息
		message = String.format(message, params);
		logger.error(showMessage);
	}
    
	// 主要用于业务型异常
	public SystemException(Integer httpCode, ErrorCodeEnum errorCodeEnum) {
		String message = errorCodeEnum.getMessage();
		if (StringUtils.isBlank(message)) {
			message = errorCodeEnum.getDefaultMessage();
		}
		resultInfo.setMessage(message);
		resultInfo.setCode(errorCodeEnum.getCode());
		resultInfo.setHttpCode(httpCode);
		logger.error(message);
		this.message = JSONUtils.toJson(resultInfo);
	}
    
	/**
	 * 主要用于业务型异常
	 * 用于扩展多个字段, 精确定义出错字段
	 * @param errorCodeEnum
	 */
	public SystemException(Integer httpCode, ErrorCodeEnum errorCodeEnum, String... params) {
		String message = errorCodeEnum.getMessage(params);
		if (StringUtils.isBlank(message)) {
			message = errorCodeEnum.getDefaultMessage();
		}
		
		resultInfo.setMessage(message);
		resultInfo.setCode(errorCodeEnum.getCode());
		resultInfo.setHttpCode(httpCode);
		logger.error(message);
		this.message = JSONUtils.toJson(resultInfo);
	}
	
	public SystemException(Integer httpCode, String code, String message) {
		resultInfo.setMessage(message);
		resultInfo.setCode(code);
		resultInfo.setHttpCode(httpCode);
		logger.error(message);
		this.message = JSONUtils.toJson(resultInfo);
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}

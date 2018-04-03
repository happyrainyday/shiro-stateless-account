package com.hzchina.common.rest.domain;

import java.text.MessageFormat;

import com.hzchina.common.service.config.RestfulConstant;

/**
 * @author agle
 * @version v 0.1 2016/06/28
 */
public enum ErrorCodeEnum {
    
	// 枚举结构介绍 --> 第一个参数是错误码，第二个参数是默认错误信息，第三个参数是自定义错误信息
	// 参数类异常, 适合扩展
	PARAMETERS_IS_NULL_1(RestfulConstant.RESULT_STATUS_PARAM_ERROR, "输入参数不能为空", "输入参数{0}不能为空"), PARAMETERS_IS_NULL_2(
			RestfulConstant.RESULT_STATUS_PARAM_ERROR, "输入参数不能为空", "输入参数{0},{1}不能为空"), PARAMETERS_IS_NOT_CORRECT(
					RestfulConstant.RESULT_STATUS_PARAM_ERROR, "参数不正确", "输入参数{0}不正确"),PARAMETERS_IS_NOT_CORRECT_2(
							RestfulConstant.RESULT_STATUS_PARAM_ERROR, "参数不正确", "{0}"),

	// 权限不足
	ERROR_PERMISSION_DENIED(RestfulConstant.RESULT_STATUS_PERMISSION_DENIED,
			RestfulConstant.RESULT_MESSAGE_PERMISSION_DENIED, RestfulConstant.RESULT_MESSAGE_PERMISSION_DENIED),
	
	// 没有错误
	NO_ERROR(RestfulConstant.RESULT_STATUS_OK, RestfulConstant.RESULT_MESSAGE_OK, RestfulConstant.RESULT_MESSAGE_OK),

	// 未找到任何记录
	ERROR_NONE(RestfulConstant.RESULT_STATUS_PARAM_ERROR, RestfulConstant.RESULT_MESSAGE_ERROR_NONE,
			RestfulConstant.RESULT_MESSAGE_ERROR_NONE),

	// 数据库异常
	ERROR_500(RestfulConstant.RESULT_STATUS_ERROR, RestfulConstant.RESULT_MESSAGE_ERROR_DB,
			RestfulConstant.RESULT_MESSAGE_ERROR_DB),

	// 一般性异常
	ERROR_PARAMETERS(RestfulConstant.RESULT_STATUS_PARAM_ERROR, RestfulConstant.RESULT_MESSAGE_PARAM_INVALID,
			RestfulConstant.RESULT_MESSAGE_PARAM_INVALID),

	// 未登录异常
	ERROR_NOT_LOGIN(RestfulConstant.RESULT_STATUS_NO_LOGIN, RestfulConstant.RESULT_MESSAGE_NO_LOGIN,
			RestfulConstant.RESULT_MESSAGE_NO_LOGIN);
	
	private String code;
	private String defaultMessage;
	private String message;

	/**
	 * 自定义错误信息 <br/>
	 * 当未支持定义错误信息时，返回默认的message信息 <b>请尽量使用该方法来自定义的明确的错误提示信息</b> 使用方法：
	 * ErrorCodeEnum.VALUES_OVER_MAX_LIMITED.getMessage("mobile_no","50"); >>
	 * mobile_no参数值最多只支持50个
	 * 
	 * @param input
	 *            自定义描述
	 * @return
	 */
	public String getMessage(String... input) {
		Object[] obj = input;
		return getMessage(obj);
	}

	/**
	 * @param input
	 * @return
	 */
	private String getMessage(Object[] input) {
		if (input == null || input.length == 0 || getMessage() == null) {
			return getDefaultMessage();
		} else {
			return MessageFormat.format(getMessage(), input);
		}
	}

	/**
	 * @param code
	 * @param defaultMessage
	 */
	private ErrorCodeEnum(String code, String defaultMessage) {
		this.code = code;
		this.defaultMessage = defaultMessage;
	}

	/**
	 * @param code
	 * @param defaultMessage
	 * @param message
	 */
	private ErrorCodeEnum(String code, String defaultMessage, String message) {
		this.code = code;
		this.defaultMessage = defaultMessage;
		this.message = message;
	}

	/**
	 * Getter method for property <tt>code</tt>.
	 * 
	 * @return property value of code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Getter method for property <tt>message</tt>.
	 * 
	 * @return property value of message
	 */
	public String getMessage() {
		return message;
	}
    
	/**
	 * 自定义外部异常信息
	 * @return 
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Getter method for property <tt>formatMessage</tt>.
	 * 
	 * @return property value of formatMessage
	 */
	public String getDefaultMessage() {
		return defaultMessage;
	}

	public void setDefaultMessage(String message) {
		this.defaultMessage = message;
	}
}
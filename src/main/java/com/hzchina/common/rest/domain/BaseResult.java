package com.hzchina.common.rest.domain;

import java.io.Serializable;

import com.hzchina.common.utils.JSONUtils;
import com.hzchina.common.utils.StringUtils;

/**
 * @Description: 基础数据返回对象
 * @author tjf
 * @date 2016年11月7日下午1:31:52
 */
public class BaseResult<T> implements Serializable {

	private static final long serialVersionUID = -814929216218701299L;
	
	// 服务返回内部码，如40001， 200暂时不变
	private String code;

	// 服务消息
	private String message;

	// 服务具体数据
	private T data;

	/**
	 * 默认构造函数
	 */
	public BaseResult() {
		super();
		this.code = ErrorCodeEnum.NO_ERROR.getCode();
		this.message = ErrorCodeEnum.NO_ERROR.getDefaultMessage();
		// 没有数据为null
		// this.data = (T) new HashMap<>();
		this.data = null;
	}

	/**
	 *
	 * @param data
	 */
	public BaseResult(T data) {
		super();
		this.code = ErrorCodeEnum.NO_ERROR.getCode(); // 内部码200不变
		this.message = ErrorCodeEnum.NO_ERROR.getDefaultMessage();
		this.data = data;
	}

	/**
	 * @param errorCodeEnum
	 */
	public BaseResult(ErrorCodeEnum errorCodeEnum) {
		super();
		this.code = errorCodeEnum.getCode();
		String message = errorCodeEnum.getMessage();
		if (StringUtils.isBlank(message)) {
			message = errorCodeEnum.getDefaultMessage();
		}
		this.message = message;
	}

	/**
	 * 用于扩展多个字段, 精确定义出错字段
	 * @param errorCodeEnum
	 */
	public BaseResult(ErrorCodeEnum errorCodeEnum, String... params) {
		super();
		this.code = errorCodeEnum.getCode();
		String message = errorCodeEnum.getMessage(params);
		if (StringUtils.isBlank(message)) {
			message = errorCodeEnum.getDefaultMessage();
		}
		this.message = message;
	}

	/**
	 *
	 * @param errorCode
	 * @param errorMessage
	 */
	public BaseResult(String errorCode, String errorMessage) {
		super();
		this.code = errorCode;
		this.message = errorMessage;
	}

	/**
	 * Getter method for property <tt>code</tt>.
	 * @return property value of code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Setter method for property <tt>code</tt>.
	 * @param code
	 * value to be assigned to property code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Getter method for property <tt>message</tt>.
	 * @return property value of message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter method for property <tt>message</tt>.
	 * @param message
	 * value to be assigned to property message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter method for property <tt>dal</tt>.
	 * @return property value of dal
	 */
	public T getData() {
		return data;
	}

	/**
	 * Setter method for property <tt>dal</tt>.
	 * @param data
	 * value to be assigned to property dal
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return JSONUtils.toJson(this);
	}

	/**
	 * Sets the error enum.
	 * @param errorCodeEnum
	 * the new error enum
	 */
	public void setErrorEnum(ErrorCodeEnum errorCodeEnum) {
		this.code = errorCodeEnum.getCode();
		this.message = errorCodeEnum.getDefaultMessage();
	}

}

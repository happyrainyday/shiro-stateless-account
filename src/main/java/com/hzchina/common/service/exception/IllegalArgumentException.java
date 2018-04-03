package com.hzchina.common.service.exception;

import com.hzchina.common.rest.domain.ErrorCodeEnum;
import com.hzchina.common.service.config.HttpStatusEnum;

/**
 * @ClassName: IllegalArgumentException
 * @Description: 主要用于参数无效性的检验
 * @author tjf
 * @date 2016年11月7日下午2:37:09
 */
public class IllegalArgumentException extends SystemException {

	private static final long serialVersionUID = 7661551017351994173L;

	public IllegalArgumentException(){
		super();
	}
	
	public IllegalArgumentException(String message) {
		super(message);
	}
	
	public IllegalArgumentException(String format, Object... params) {
		super(format, params);
	}
	
	// 主要用于默认400
	public IllegalArgumentException(ErrorCodeEnum errorCodeEnum) {
		super(HttpStatusEnum.HTTP_STATUS_PARAM_ERROR.getCode(), errorCodeEnum);
	}
	
	// 主要用于业务型异常
	public IllegalArgumentException(Integer httpCode, ErrorCodeEnum errorCodeEnum) {
		super(httpCode, errorCodeEnum);
	}
    
	/**
	 * 主要用于业务型异常
	 * 用于扩展多个字段, 精确定义出错字段
	 * @param errorCodeEnum
	 */
	public IllegalArgumentException(Integer httpCode, ErrorCodeEnum errorCodeEnum, String... params) {
		super(httpCode, errorCodeEnum, params);
	}
}

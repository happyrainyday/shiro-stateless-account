package com.hzchina.account.service.exception;

import com.hzchina.common.rest.domain.ErrorCodeEnum;
import com.hzchina.common.service.config.HttpStatusEnum;
import com.hzchina.common.service.exception.SystemException;

/**
 * @ClassName: AccountException
 * @Description: 账户异常处理
 * @author tjf
 * @date 2016年9月28日下午2:50:23
 * @Version V1.00
 */
public class AccountException extends SystemException {
    
	private static final long serialVersionUID = 8710148750320023669L;

	public AccountException() {
        super();
    }

    public AccountException(String message) {
        super(message);
    }

    public AccountException(String format, Object... params)
    {
    	super(format, params);
    }

	// 主要用于业务型异常
	public AccountException(Integer httpCode, ErrorCodeEnum errorCodeEnum) {
		super(httpCode, errorCodeEnum);
	}
    
	// 常用的业务型400异常
	public AccountException(ErrorCodeEnum errorCodeEnum) {
		super(HttpStatusEnum.HTTP_STATUS_PARAM_ERROR.getCode(), errorCodeEnum);
	}
	
	/**
	 * 主要用于业务型异常
	 * 用于扩展多个字段, 精确定义出错字段
	 * @param errorCodeEnum
	 */
	public AccountException(Integer httpCode, ErrorCodeEnum errorCodeEnum, String... params) {
		super(httpCode, errorCodeEnum, params);
	}
	
	// 业务型 200， 500的异常
	public AccountException(Integer httpCode, String code, String message) {
		super(httpCode, code, message);
	}
	
	// 常用的业务型400异常
	public AccountException(String code, String message) {
		super(HttpStatusEnum.HTTP_STATUS_PARAM_ERROR.getCode(), code, message);
	}
}

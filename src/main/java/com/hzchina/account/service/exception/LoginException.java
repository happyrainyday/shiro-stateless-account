package com.hzchina.account.service.exception;

import com.hzchina.common.rest.domain.ErrorCodeEnum;
import com.hzchina.common.service.config.HttpStatusEnum;
import com.hzchina.common.service.exception.SystemException;

/**
 * @ClassName: LoginException
 * @Description: 登录异常
 * @author tjf
 * @date 2016年10月13日上午9:30:16
 * @Version V1.00
 */
public class LoginException extends SystemException {

	private static final long serialVersionUID = 4927322161736443832L;
	
	public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String format, Object... params)
    {
    	super(format, params);
    }
    
    // 业务型异常
    public LoginException(ErrorCodeEnum errorCodeEnum) {
		super(HttpStatusEnum.HTTP_STATUS_NO_LOGIN.getCode(), errorCodeEnum);
	}
    
	// 主要用于业务型异常
	public LoginException(Integer httpCode, ErrorCodeEnum errorCodeEnum) {
		super(httpCode, errorCodeEnum);
	}
    
	/**
	 * 主要用于业务型异常
	 * 用于扩展多个字段, 精确定义出错字段
	 * @param errorCodeEnum
	 */
	public LoginException(Integer httpCode, ErrorCodeEnum errorCodeEnum, String... params) {
		super(httpCode, errorCodeEnum, params);
	}
}

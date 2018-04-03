package com.hzchina.common.rest.domain;

public class ResultInfo {
	
	private Integer httpCode; // http状态码
	
	private String code;    // 状态
	
	private String message; // 消息
	
	private Object data;    // 数据

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(Integer httpCode) {
		this.httpCode = httpCode;
	}

}

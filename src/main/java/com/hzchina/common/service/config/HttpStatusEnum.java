package com.hzchina.common.service.config;

public enum HttpStatusEnum {
	HTTP_STATUS_OK(200, "OK"), HTTP_STATUS_INVALID(300, "请求无效"), HTTP_STATUS_PARAM_ERROR(400,
			"请求参数不正确"), HTTP_STATUS_NO_LOGIN(401, "尚未登录"), HTTP_STATUS_PERMISSION_DENIED(403,
					"权限不足"), HTTP_STATUS_NOT_FOUND(404,
							"请求地址不正确"), HTTP_STATUS_NOT_METHOD(405, "请求方法不正确"), HTTP_STATUS_ERROR(500, "服务器出错");
	
	private Integer code;
	private String msg;

	private HttpStatusEnum(Integer code, String msg) {
			this.code = code;
			this.msg = msg;
		}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return this.msg;
	}
}

package com.hzchina.common.service.config;

/**
 * @description 系统常量
 * @author agle
 * @time 2016年01月19日
 */
public class RestfulConstant {

	public final static String TEST_USERID = "2015102014121865123e23767f2ad425f955e4563b6d8cf99";// 测试用户
	public final static String TEST_ACCOUNTID = "20151020141218661bdd677c931194b80bb9a378a368e9acf";
	public final static String ITEMTYPE = "all";
	public final static String RESULT_STATUS_OK = "200"; // 请求成功
	public final static String RESULT_STATUS_INVALID = "300"; // 请求无效
	public final static String RESULT_STATUS_PARAM_ERROR = "400"; // 请求参数不正确
	public final static String RESULT_STATUS_NO_LOGIN = "401"; // 尚未登录
	public final static String RESULT_STATUS_PERMISSION_DENIED = "403"; // 权限不足
	public final static String RESULT_STATUS_NOT_FOUND = "404"; // 请求地址不正确
	public final static String RESULT_STATUS_NOT_METHOD = "405"; // 请求方法不正确
	public final static String RESULT_STATUS_ERROR = "500"; // 服务器出错

	public final static String RESULT_MESSAGE_OK = "OK";
	public final static String RESULT_MESSAGE_ERROR_NOT_FOUND = "Not Found";
	public final static String RESULT_MESSAGE_ERROR_NOT_METHOD = "Method Not Allowed";
	public final static String RESULT_MESSAGE_NO_LOGIN = "用户尚未登录";
	public final static String RESULT_MESSAGE_PERMISSION_DENIED = "用户权限不足";
	public final static String RESULT_MESSAGE_ERROR_NONE = "未找到任何记录";
	public final static String RESULT_MESSAGE_ERROR_ADD = "添加数据失败";
	public final static String RESULT_MESSAGE_ERROR_UPDATE = "更新数据失败";
	public final static String RESULT_MESSAGE_ERROR_DELETE = "删除数据失败";
	public final static String RESULT_MESSAGE_ERROR_FIND = "查询数据失败";
	public final static String RESULT_MESSAGE_ERROR_DB = "数据库出错";

	public final static String RESULT_MESSAGE_PARAM_NOT = "缺少必入参数";
	public final static String RESULT_MESSAGE_PARAM_TYPE_ERROR = "参数类型不正确";
	public final static String RESULT_MESSAGE_PARAM_INVALID = "参数无效";
    
    public final static String SYSTEM_ERROR = "系统异常";
    public final static String SERVICE_ERROR = "服务异常";
    
    // 内部码系列
    public final static String INTERNAL_ERROR_CODE_40000 = "40000";
    public final static String INTERNAL_ERROR_CODE_40001 = "40001";
    public final static String INTERNAL_ERROR_CODE_40002 = "40002";
    public final static String INTERNAL_ERROR_CODE_40003 = "40003";
    
    // 50000系列错误内部码
    public final static String INTERNAL_ERROR_CODE_50000 = "50000";
    public final static String INTERNAL_ERROR_CODE_50001 = "50001";
}

package com.hzchina.account.security;

/**
 * @ClassName: Constants
 * @Description: 常量设置
 * @author tjf
 * @date 2016年9月22日下午3:15:24
 * @Version V1.00
 */
public class SecurityConstants {
	
	public static final String PARAM_USERNAME = "username";
	public static final String PARAM_PASSWORD = "password";
	public static final String SECRET_KEY = "taihe@vip.key";  // token加密密钥
	public static final Long EXPIRATION = 30 * 60 * 1000L;    // token的过期时间
	public static final Long REFRESH_TIME = 10 * 60 * 1000L;  // token刷新时间
	public static final String HEADER = "X-Auth-Token";       // 携带token的字段
	
	// 加盐配置
	public static final String HASH_ALGORITHM = "SHA-1";      // 加密算法
    public static final int HASH_INTERATIONS = 1024;          // 迭代次数
	public static final int SALT_SIZE = 8;

}

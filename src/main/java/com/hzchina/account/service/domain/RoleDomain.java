/**
 * @Title: RoleVo.java
 * @Package com.tairanchina.account.vo
 * @Description: TODO(用一句话描述该文件做什么)
 * @author tjf
 * @date 2016年4月8日
 * @version V1.0
 */
package com.hzchina.account.service.domain;

/**
 * @ClassName: RoleVo
 * @Description: RoleVo
 * @author tjf
 * @date 2016年4月8日下午5:48:57
 * @Version V1.00
 */
public class RoleDomain {

	private String authorityCd;
	private String authorityName;
	
	public String getAuthorityCd() {
		return authorityCd;
	}
	public void setAuthorityCd(String authorityCd) {
		this.authorityCd = authorityCd;
	}
	public String getAuthorityName() {
		return authorityName;
	}
	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}
}

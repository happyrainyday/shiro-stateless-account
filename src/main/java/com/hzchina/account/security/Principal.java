package com.hzchina.account.security;

import java.io.Serializable;
import java.util.List;

import com.hzchina.account.dal.entity.User;

/**
 * @ClassName: Principal
 * @Description: 授权信息
 * @author tjf
 * @date 2016年4月18日下午3:18:49
 * @Version V1.00
 */
public class Principal implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String uid;
	private String username;
	private List<String> authorites;
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public List<String> getAuthorites() {
		return authorites;
	}

	public void setAuthorites(List<String> authorites) {
		this.authorites = authorites;
	}

	public Principal(User user) {
		this.uid = user.getUid();
		this.username = user.getUsername();
	}
}

package com.hzchina.account.rest.dto;

public class PasswordDto {

	private String password;
	private String newPassword; //新密码
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}

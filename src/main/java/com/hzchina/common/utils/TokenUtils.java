package com.hzchina.common.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hzchina.account.security.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtils {

	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	/**
	 * 返回密码
	 * @param token
	 * @return
	 */
	public String getPasswordFromToken(String token) {
		String password;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			password = (String) claims.get("password");
		} catch (Exception e) {
			password = null;
		}
		return password;
	}

	public Date getCreatedDateFromToken(String token) {
		Date created;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			created = new Date((Long) claims.get("created"));
		} catch (Exception e) {
			created = null;
		}
		return created;
	}

	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	public String getAudienceFromToken(String token) {
		String audience;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			audience = (String) claims.get("audience");
		} catch (Exception e) {
			audience = null;
		}
		return audience;
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET_KEY).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	private Date generateCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION);
	}

	// 根据自己的需求生成对用的token
	public String generateToken(String userName, String password) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("sub", userName);
		claims.put("created", this.generateCurrentDate());
		claims.put("password", password); // 自定义password字段
		return this.generateToken(claims);
	}

	private String generateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(this.generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET_KEY).compact();
	}

	public Boolean canTokenBeRefreshed(String token) {
		Date created = this.getCreatedDateFromToken(token);
		Date refreshDate = this.generateCurrentDate();
		if (null == created || null == refreshDate) {
			return false;
		}
		// 每过十分钟刷新token
		return (refreshDate.getTime() - created.getTime()) >= SecurityConstants.REFRESH_TIME ? true : false;
	}

	public String refreshToken(String token) {
		String refreshedToken;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			claims.put("created", this.generateCurrentDate());
			refreshedToken = this.generateToken(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	private Boolean isAccessTokenExpired(String token) {
		Date created = this.getCreatedDateFromToken(token);
		if (null == created) {
			return false; // 解析时间错误直接认为token过期
		}

		Date expiration = new Date(created.getTime() + SecurityConstants.EXPIRATION);
		return expiration.before(this.generateCurrentDate());
	}

	public Boolean validateToken(String token) {
		return !(this.isAccessTokenExpired(token));
	}

}

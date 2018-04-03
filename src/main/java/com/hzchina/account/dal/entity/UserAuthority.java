package com.hzchina.account.dal.entity;

public class UserAuthority {
    private Long id;

    private String uid;

    private String authorityCd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getAuthorityCd() {
        return authorityCd;
    }

    public void setAuthorityCd(String authorityCd) {
        this.authorityCd = authorityCd == null ? null : authorityCd.trim();
    }
}
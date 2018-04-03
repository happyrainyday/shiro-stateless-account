package com.hzchina.account.dal.entity;

public class Authority {
    private String authorityCd;

    private String authorityName;

    private String authorityDescription;

    public String getAuthorityCd() {
        return authorityCd;
    }

    public void setAuthorityCd(String authorityCd) {
        this.authorityCd = authorityCd == null ? null : authorityCd.trim();
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName == null ? null : authorityName.trim();
    }

    public String getAuthorityDescription() {
        return authorityDescription;
    }

    public void setAuthorityDescription(String authorityDescription) {
        this.authorityDescription = authorityDescription == null ? null : authorityDescription.trim();
    }
}
package com.hzchina.account.dal.dao;

import java.util.List;

import com.hzchina.account.dal.entity.Authority;

public interface AuthorityMapper {
    int deleteByPrimaryKey(String authorityCd);

    int insert(Authority record);

    int insertSelective(Authority record);

    Authority selectByPrimaryKey(String authorityCd);

    int updateByPrimaryKeySelective(Authority record);

    int updateByPrimaryKey(Authority record);
    
    List<Authority> getAuthorityList();
}
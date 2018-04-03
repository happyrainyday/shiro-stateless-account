package com.hzchina.account.dal.dao;

import java.util.List;

import com.hzchina.account.dal.entity.UserAuthority;

public interface UserAuthorityMapper {
	int deleteByPrimaryKey(Long id);

	int deleteByUid(String uid);

	int insert(UserAuthority record);

	int insertSelective(UserAuthority record);

	UserAuthority selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(UserAuthority record);

	int updateByPrimaryKey(UserAuthority record);

	List<UserAuthority> selectByUid(String uid);
}
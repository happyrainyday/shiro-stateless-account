package com.hzchina.account.dal.dao;

import java.util.HashMap;
import java.util.List;

import com.hzchina.account.dal.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(String uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String uid);
    
    User selectByAdmin(String uid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
	
	List<User> getUserList(HashMap<String, Object> conditions);
	
	int blackOrWhiteUser(HashMap<String, Object> conditions);
	
	User getUserbyUsername(String username);
	
	User selectByIdNotActivated(String uid);
	
	long selectAllUserCount(HashMap<String, Object> conditions);
}

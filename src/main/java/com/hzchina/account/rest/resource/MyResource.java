package com.hzchina.account.rest.resource;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hzchina.account.dal.dao.UserMapper;
import com.hzchina.account.dal.entity.User;
import com.hzchina.common.rest.domain.BaseResult;

@Path("myresource")
public class MyResource {
	
	private Logger logger = LoggerFactory.getLogger(MyResource.class);
	
	@Autowired
	UserMapper userMapper;
//	@Autowired
//	TempletMapper templetMapper;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
//		System.out.println(templetMapper);
//		User user=userMapper.getUserbyUsername("xiaotai");
		return "hheh";
	}

	@GET
	@Path("test")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getuser() {
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("username", "x");

		conditions.put("pageIndex", 0);
		conditions.put("pageSize", 10);
		List<User> list = userMapper.getUserList(conditions);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getFullName());

		}
		System.out.println(userMapper.selectAllUserCount(conditions));
		return Response.status(200).entity("").build();
	}
	
	@GET
	@Path("oktest")
	@Produces(MediaType.APPLICATION_JSON)
	public BaseResult<?> getTest(@QueryParam("num") String num) {
		logger.info("我进来了，你们要小心呀");
		switch (Integer.parseInt(num)) {
		case 1:
//			throw new AccountException("账号异常");
//		    throw new NotAllowedException(Response.status(200).entity("xixixi").build());
			throw new WebApplicationException(Response.status(200).entity("").build());
		default:
			break;
		}
		return new BaseResult<>("呼噜娃" + num + "来了");
	}
	
}

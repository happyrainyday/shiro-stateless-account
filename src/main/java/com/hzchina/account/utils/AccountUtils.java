package com.hzchina.account.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.hzchina.account.rest.dto.UserDto;

/**
 * @ClassName: AccountUtils
 * @Description: 账户工具
 * @author tjf
 * @date 2016年10月12日下午5:13:00
 * @Version V1.00
 */
public class AccountUtils {

	/**
	 * @Name: checkUserDto
	 * @Description: 检查userDto参数的有效性，结合前台校验，同时为了安全性，后台可以根据具体需要做更加具体的限制
	 * @Author: tjf
	 * @Version: V1.00
	 * @CreateDate: 2016年4月11日上午9:35:20
	 * @param userDto
	 * @Return: boolean 返回类型
	 */
	public static boolean checkUserDto(UserDto userDto) {
		// 如果断言出错，则程序必然有问题
		Assert.notNull(userDto);

		if (StringUtils.isNotBlank(userDto.getFullName()) || StringUtils.isNotBlank(userDto.getPassword())
				|| null != userDto.getActivated()) {
			return true;
		}

		return false;
	}
}

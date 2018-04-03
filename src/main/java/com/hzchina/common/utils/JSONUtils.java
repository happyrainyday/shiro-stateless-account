package com.hzchina.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @ClassName: JSONUtils
 * @Description: JSON<->Bean之间转化
 * @author tjf
 * @date 2016年6月20日下午4:11:06
 * @Version V1.00
 */
public class JSONUtils {

	public static ObjectMapper objectMapper;

	static {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(Include.NON_NULL);// 序列化的时候忽略空值
			objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);// 按照首字母排序
		}
	}

	public static <T> String toJson(T t) {
		try {
			return objectMapper.writeValueAsString(t);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// 将json按Tree的形式迭代每个节点
	public static JsonNode getJsonNode(String jsonStr) {
		try {
			return objectMapper.readTree(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 取JSON一级节点值
	public static String getValue(String jsonStr, String key) {
		try {
			JsonNode jsonNode = objectMapper.readTree(jsonStr);
			return jsonNode.get(key).asText(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T toBean(String jsonStr, TypeReference<T> typeRef) {
		try {
			return objectMapper.readValue(jsonStr, typeRef);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T toBean(String jsonStr, Class<T> clazz) {
		try {
			return objectMapper.readValue(jsonStr, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
		
}

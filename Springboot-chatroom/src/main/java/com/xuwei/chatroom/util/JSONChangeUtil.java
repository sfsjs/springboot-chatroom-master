package com.xuwei.chatroom.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author xuwei
 * @Date 2020/9/7 16:02
 * @Description 封装Jackson操作
 */
public class JSONChangeUtil {
    private JSONChangeUtil() {
    }

    /**
     * json转换成对象
     * @param obj 传入对象
     * @return json字符串
     */
    public static Object jsonToObj(Object obj, String jsonStr) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonStr, obj.getClass());
    }


    /**
     * 对象转换成json字符串
     * @param obj 传入对象
     * @return json字符串
     */
    public static String objToJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

}

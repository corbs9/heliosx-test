package org.heliosx.consultation.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;

public final class JsonMapper {

    static {
        JSON.config(JSONWriter.Feature.WriteNullListAsEmpty);
    }

    public static String fromObject(Object object) {
        return JSON.toJSONString(object);
    }

    public static <T> T fromString(String string, Class<T> clazz) {
        return JSON.parseObject(string, clazz);
    }
}

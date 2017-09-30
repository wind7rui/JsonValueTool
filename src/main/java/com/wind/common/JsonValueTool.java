package com.wind.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * 用于获取json字符串中指定key的value
 *
 * @author wind
 * @since 05.05.2015
 */
public class JsonValueTool {

    /**
     * 用于获取json字符串中指定key的value
     *
     * 示例：
     * json：{"name": "刘禅", "age": "6", "father": {"name": "刘备", "age": "50", "properties": {"country":{"id":"002","name": "蜀"}}}}
     *
     * 要获取country中的name值
     * startKey = father
     * targetKeyExpression = father#properties#country#name
     *
     * @param json
     * @param startKey
     * @param targetKeyExpression 使用#连接
     * @return
     */
    public static Object getValueByKeyExpression(String json, String startKey, String targetKeyExpression) {
        return doGetValueByKeyExpression(JSONObject.parseObject(json), startKey, targetKeyExpression);
    }

    private static Object doGetValueByKeyExpression(Object originObject, String startKey, String targetKeyExpression) {
        if (isNullOrEmpty(startKey)) {
            return originObject;
        }
        if (originObject instanceof JSONObject) {
            return getValueFromJSONObjectByKeyExpression((JSONObject) originObject, startKey, targetKeyExpression);
        }
        if (originObject instanceof JSONArray) {
            return getValueFromJSONArrayByKeyExpression((JSONArray) originObject, startKey, targetKeyExpression);
        }
        return null;
    }

    private static String getNextKey(String startKey, String targetKeyExpression) {
        if (isNullOrEmpty(targetKeyExpression)) {
            return null;
        }

        String[] keys = targetKeyExpression.split("#");
        for (int i = 0; i < keys.length; i++) {
            if (keys[i].equals(startKey) && (i < keys.length - 1)) {
                return keys[i + 1];
            }
        }
        return null;
    }

    private static Object getValueFromJSONArrayByKeyExpression(JSONArray originObject, String startKey, String targetKeyExpression) {
        for (int j = 0; j < originObject.size(); j++) {
            JSONObject jsonObject = originObject.getJSONObject(j);
            Object targetObject = getValueFromJSONObjectByKeyExpression(jsonObject, startKey, targetKeyExpression);
            if (targetObject != null) {
                return targetObject;
            }
        }
        return null;
    }

    private static Object getValueFromJSONObjectByKeyExpression(JSONObject originObject, String startKey, String targetKeyExpression) {
        Object object = originObject.get(startKey);
        return object != null ? doGetValueByKeyExpression(object, getNextKey(startKey, targetKeyExpression), targetKeyExpression) : null;
    }
}

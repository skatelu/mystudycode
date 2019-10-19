package com.szkingdom.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by ZWQ on 2018/6/12.for Jeson组解包
 * 使用了阿里的Json转换工具fastjson来做封装，实现了对象和Json互转，
 * 对象数组和Json互转，list和Json互转，Map和Json互转。
 * 在项目中可以直接使用该工具类来做Json和其他对象的转换。
 */

public class JsonUtil {
    private static final SerializeConfig config;

    static {
        config = new SerializeConfig();
        // compatible with the java.util.Date and the java.sql.Date
        config.put(java.util.Date.class, new JSONLibDataFormatSerializer());     // 使用和json-lib兼容的日期输出格式
        config.put(java.sql.Date.class, new JSONLibDataFormatSerializer());       // 使用和json-lib兼容的日期输出格式
    }

    /**
     * set the default value of the object which the value is null
     */
    private static final SerializerFeature[] features = {
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.WriteNullBooleanAsFalse,
            SerializerFeature.WriteNullStringAsEmpty,
    };

    /**
     * list to json
     *
     * @param list
     *          the list that will transform to json string
     * @return
     *          the json string of list transform
     */
    public static String list2json(List list) {
        return JSON.toJSONString(list);
    }

    /**
     * map to json
     * @param map
     *          the map that will transform to json string
     * @return
     *          the json string of map transform
     */
    public static String map2json(Map map) {
        return JSONObject.toJSONString(map);
    }

    /**
     * object array to json
     *
     * @param objects
     *          the object array that will transform to json string
     * @return
     *          the json string of array transform
     */
    public static String array2json(Object[] objects) {
        return JSON.toJSONString(objects);
    }

    /**
     * object to json
     *
     * @param object
     *          the object that will transform to json string
     * @return
     *          the json string of object
     */
    public static String object2json(Object object) {
        return JSON.toJSONString(object, config, features);
    }


    /**
     * json to list
     *
     * @param json
     *          the json string that will transform to list
     * @param clazz
     *          the class of the list's element
     * @param <T>
     *          the generic of the class
     * @return
     *          the list that json string transform
     */
    public static <T> List<T> json2list(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    /**
     * json to map
     *
     * @param json
     *          json string that will transform to map
     * @return
     *          the map fo json string
     */
    public static Map json2map(String json) {
        return JSONObject.parseObject(json);
    }


    /**
     * json string to object array
     *
     * @param json
     *          the json string will transform to object array
     * @param clazz
     *          the class of the json will transform
     * @param ts
     *          the real object array
     * @param <T>
     *          the real object
     * @return
     *          the object array of the json string
     *
     * @param json
     * @param clazz
     * @param ts
     * @param <T>
     * @return
     */
    public static <T> T[] json2array(String json, Class<T> clazz, T[] ts) {
        return JSON.parseArray(json, clazz).toArray(ts);
    }

    /**
     * json string to object
     *
     * @param json
     *          the json string that will transform to object
     * @param clazz
     *          the class that json will transform
     * @param <T>
     *          the object class
     * @return
     *          the object of json string
     */
    public static <T> Object json2object(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /*********************zwq
     /**
     * 将string转化为序列化的json字符串
     * * @param text
     * * @return
     * */
    public static Object text2Json(String text)
    {
        Object objectJson  = JSON.parse(text);
        return objectJson;
    }

    /**
     * Bean对象转JSON
     *
     * @param object
     * @param dataFormatString
     * @return
     */
    public static String beanToJson(Object object, String dataFormatString) {
        if (object != null) {
            if (StringUtils.isEmpty(dataFormatString)) {
                return JSONObject.toJSONString(object);
            }
            return JSON.toJSONStringWithDateFormat(object, dataFormatString);
        } else {
            return null;
        }
    }

    /**
     * Bean对象转JSON
     *
     * @param object
     * @return
     */
    public static String beanToJson(Object object) {
        if (object != null) {
            return JSON.toJSONString(object);
        } else {
            return null;
        }
    }

    /**
     * String转JSON字符串
     *
     * @param key
     * @param value
     * @return
     */
    public static String string2Json(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put(key, value);
        return beanToJson(map, null);
    }

    /**
     * 将json字符串转换成对象
     *
     * @param json
     * @param clazz
     * @return
     */
    public static Object jsonToBean(String json, Object clazz) {
        if (StringUtils.isEmpty(json) || clazz == null) {
            return null;
        }
        return JSON.parseObject(json, clazz.getClass());
    }

    /**
     * json字符串转map
     *
     * @param json
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonToMap(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return JSON.parseObject(json, Map.class);
    }

//    ////////////////233
//    public static List string2List(String json) {
//        JSONArray array = JSONObject.parseArray(json)  ;
//        return array.toJavaList(Map.class);
//    }
//
//    public static String list2JSONString(  List list) {
//        String listJson = JSON.toJSONString(list, true);
//        return  listJson;
//    }
//
//    public static JSONArray string2JsonArray(String json) {
//        return    JSONObject.parseArray(json)  ;
//    }
//
//   public static String list2String(JSONArray jsonArray) {
//       String jsonStr = JSON.toJSONString(jsonArray, true);
//       return  jsonStr;
//    }

}

package com.frye.trading.utils;

import com.alibaba.fastjson.JSON;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义json打包类
 */

public class DataJsonUtils {

    private int code;    // 状态码
    private String msg;  // 消息
    private int count;   // 数据条数
    private Object data; // 数据

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("code", code);
        map.put("msg", msg);
        map.put("count", count);
        map.put("data", data);
        return JSON.toJSONString(map);
    }
}

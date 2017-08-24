package com.hand.dto;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 铭刻 on 2017/8/23.
 */
public class Msg {
    private int code;
    private String msg;
    private Map<String ,Object> map = new HashMap<String,Object>();

    public static Msg success(){
        Msg msg = new Msg();
        msg.code=100;
        msg.msg="成功";
        return msg;
    }

    public static Msg fail(){
        Msg msg = new Msg();
        msg.code=200;
        msg.msg="失败";
        return msg;
    }

    public Msg add(String key,Object value){
        this.map.put(key,value);
        return this;
    }

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

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}

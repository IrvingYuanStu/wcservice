package com.irving.wcs.common.pojo;

/**
 * @Description 响应基础类
 * @Author yuanyc
 * @Date 2018/12/6 11:45 AM
 **/
public class Response {

    /** 状态码 */
    private int stateCode;

    /** 返回信息 */
    private String msg;

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

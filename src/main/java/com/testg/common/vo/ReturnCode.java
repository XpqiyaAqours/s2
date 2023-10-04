package com.testg.common.vo;

public enum ReturnCode {
    RC200(200, "操作成功"),
    RC500(500, "操作失败");

    // 自定义状态码
    private final int code;

    // 自定义描述
    private final String msg;

    ReturnCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}


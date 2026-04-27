package com.s008.smartcanteen.common;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;    // 200-成功, 400-业务错误, 500-崩溃 [cite: 71]
    private String msg;
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.code = 200;
        r.msg = "success";
        r.data = data;
        return r;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> r = new Result<>();
        r.code = 400; // 业务逻辑阻断 [cite: 105]
        r.msg = msg;
        return r;
    }
}
package com.smart.cms.exception;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2022/3/3 17:19
 * @Version: 1.0
 */
public class GlobalExceptionHandle extends RuntimeException {
    private int code; // 状态码
    private String message;

    public GlobalExceptionHandle( String message, int code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public GlobalExceptionHandle(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}

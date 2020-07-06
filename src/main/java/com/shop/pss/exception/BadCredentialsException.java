package com.shop.pss.exception;

/**
 * @author 王瑞
 * @description
 * @date 2019-02-25 23:29
 */
public class BadCredentialsException extends RuntimeException {

    public BadCredentialsException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;
}

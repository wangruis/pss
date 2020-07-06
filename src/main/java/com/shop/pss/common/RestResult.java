package com.shop.pss.common;

import com.google.common.base.MoreObjects;
import com.shop.pss.enums.ResultEnum;

/**
 * @creator 王瑞
 * @createtime 2019/2/25 15:55
 * @description:
 * @param <T>
 */
public class RestResult<T> {
    public static final String SUCCESS_CODE = "0";
    private static final String SUCCESS_MSG = "success";

    private String errcode = "0";

    private String errmsg = "";

    private T data = null;


    public static RestResult success() {
        return new RestResult(SUCCESS_CODE, SUCCESS_MSG);
    }

    public static RestResult error() {
        return new RestResult(ResultEnum.UNKNOWN_ERROR);
    }

    /**
     * default RestResult with data and success code.
     * @param data response data
     * @param <T> response type
     * @return rest result
     */
    public static <T> RestResult success(T data) {
        RestResult<T> result = new RestResult<>(SUCCESS_CODE, SUCCESS_MSG);
        result.setData(data);
        return result;
    }
    public static <T> RestResult error(ResultEnum resultEnum) {
        RestResult<T> result = new RestResult<>(resultEnum);
        return result;
    }

    public static <T> RestResult error(ResultEnum resultEnum, T data) {
        RestResult<T> result = new RestResult<>(resultEnum);
        result.setData(data);
        return result;
    }

    public RestResult() {
    }

    public RestResult(String errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public RestResult(ResultEnum resultEnum) {
        this.errcode = resultEnum.getCode();
        this.errmsg = resultEnum.getMsg();
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("errcode", errcode)
                .add("errmsg", errmsg)
                .add("data", data)
                .toString();
    }
}

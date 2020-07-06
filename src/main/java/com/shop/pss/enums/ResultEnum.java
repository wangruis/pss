package com.shop.pss.enums;

/**
 * 返回结果枚举.
 *
 * @creator 王瑞
 * @createtime 2019/2/25 15:57
 * @description:
 **/
public enum ResultEnum {
    UNKNOWN_ERROR("-1", "服务器繁忙"),
    SUCCESS("0", "success"),
    TENANT_NOT_EXIST("200001", "用户不存在"),
    USER_NOT_EXIST("200002", "token为空"),
    NOT_AUTHORIZED("200003", "未通过认证鉴权"),
    TOKEN_ERROR("200004", "token认证失败"),
    TOKEN_TIMEOUT("200005", "token已失效"),

    ROLE_ERROR("200011", "权限不足"),

    USERNAME_PASSWORD_ERROR("200021", "账户名或者密码输入错误"),
    USERNAME_LOCK("200022", "账户被锁定"),
    PASSWORD_OVERDUE("200023", "密码过期"),
    USERNAME_OVERDUE("200024", "账户过期"),
    USERNAME_PROHIBIT("200025", "账户被禁用"),
    LOGIN_ERROR("200026", "登录失败"),
    LOGIN_OUT("200026", "尚未登录，请登录"),

    PWD_UPDATE_UNKONOW("200031", "旧密码输入有误"),
    PWD_UPDATE_ERROR("200032", "密码修改失败"),
    USER_IS_NULL("200033", "输入的身份证号和登录名不匹配"),
    USER_IS_NOT_NULL("200034", "不能新增已存在用户或已被删除用户的用户名相同"),
    REG_STUDENT_USER_ERROR("200034", "新增学生用户失败"),


    PUT_STOCK_ERROR("200041", "入库失败"),
    OUT_STOCK_ERROR("200042", "出库失败"),
    DELETE_STOCK_ERROR("200043", "删库失败"),
    STOCK_NUM_TATOL("200044", "库存数量少于出库数量"),
    SCORE_DEFICIENCY("200045", "卡上积分不足"),
    CARD_UNKNOW("200046", "无该卡号的记录"),
    GOODS_NUM("200047", "商品数量不能为负数"),
    GOODS_SCORE("200048", "商品积分不能为负数"),
    GOODS_NULL("200049", "该商品暂无记录"),
    SCORE_NULL("200050", "该卡暂无可用积分"),


    MKDIR_ERROR("200051", "文件夹创建失败"),
    EXPORT_EXCEL("200052", "导出商品信息失败");


    // 编码
    private String code;
    // 信息
    private String msg;

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
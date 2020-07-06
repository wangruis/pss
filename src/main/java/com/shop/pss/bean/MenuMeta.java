package com.shop.pss.bean;

import java.io.Serializable;

/**
 * @author 王瑞
 * @description
 * @date 2019-02-26 19:01
 */
public class MenuMeta implements Serializable {

    private boolean keepAlive;
    private boolean requireAuth;

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public boolean isRequireAuth() {
        return requireAuth;
    }

    public void setRequireAuth(boolean requireAuth) {
        this.requireAuth = requireAuth;
    }
}

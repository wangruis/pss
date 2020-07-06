package com.shop.pss.common;

import com.shop.pss.bean.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author 王瑞
 * @description
 * @date 2019-02-26 19:01
 */
public class UserUtils {
    public static User getCurrentHr() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

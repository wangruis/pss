package com.shop.pss.controller;

import com.shop.pss.common.RestResult;
import com.shop.pss.enums.ResultEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王瑞
 * @description
 * @date 2019-02-25 16:05
 */
@RestController
public class LoginController {

    /**
     * @return
     * @creator 王瑞
     * @createtime 2019/2/26 19:05
     * @description: 未登录提示
     */
    @RequestMapping(value = {"/login_p",""})
    public RestResult login() {
        return RestResult.error(ResultEnum.LOGIN_OUT);
    }

}

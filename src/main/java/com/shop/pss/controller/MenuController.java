package com.shop.pss.controller;

import com.shop.pss.common.RestResult;
import com.shop.pss.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王瑞
 * @description 菜单
 * @date 2019-02-26 19:01
 */
@RestController
@RequestMapping("/pss/api/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    /**
     * @return
     * @creator 王瑞
     * @createtime 2019/2/26 19:55
     * @description: 
     */
    @PostMapping("/sysmenu")
    public RestResult sysmenu() {
        return RestResult.success(menuService.getMenusByHrId());
    }

}

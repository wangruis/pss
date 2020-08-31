package com.shop.pss.controller;

import com.shop.pss.bean.User;
import com.shop.pss.common.RestResult;
import com.shop.pss.common.UserUtils;
import com.shop.pss.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 王瑞
 * @description
 * @date 2019-02-26 19:01
 */
@RestController
@RequestMapping("/pss/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @return
     * @creator 王瑞
     * @createtime 2019/2/26 19:08
     * @description:
     */
    @PostMapping("/info")
    public RestResult currentUser() {
        return RestResult.success(UserUtils.getCurrentHr());
    }

    /**
     * @creator 王瑞
     * @createtime 2019/6/4 15:07
     * @description: 查询所有用户
     */
    @GetMapping("/get_all_user")
    public RestResult getAllHr() {
        return RestResult.success(userService.getAllUser());
    }

    @PostMapping("/set_pwd")
    public RestResult setPassword(
            @RequestParam(value = "oldPwd", required = true) String oldPwd,
            @RequestParam(value = "newPwd", required = true) String newPwd) {
        return userService.changePwd(oldPwd, newPwd);
    }

    /**
     * @creator 王瑞
     * @createtime 2019/6/4 14:44
     * @description: 找回密码
     */
    @PostMapping("/retrieve_pwd")
    public RestResult retrievePwd(
            @RequestParam(value = "idCard", required = true) String idCard,
            @RequestParam(value = "userName", required = true) String userName,
            @RequestParam(value = "pwd", required = true) String pwd) {

        return userService.retrievePwd(idCard, userName, pwd);
    }

    /**
     * @creator 王瑞
     * @createtime 2019/6/4 14:44
     * @description: 修改用户
     */
    @PostMapping("/update_user")
    public RestResult updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    /**
     * @creator 王瑞
     * @createtime 2019/6/4 14:44
     * @description: 添加学生用户
     */
    @PostMapping("/reg_student_user")
    public RestResult regStudentUser(@RequestBody User user){
        return userService.regStudentUser(user);
    }

}

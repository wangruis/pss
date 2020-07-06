package com.shop.pss.service;

import com.google.common.base.Strings;
import com.shop.pss.bean.Role;
import com.shop.pss.bean.User;
import com.shop.pss.common.RestResult;
import com.shop.pss.common.UserUtils;
import com.shop.pss.dao.RoleMapper;
import com.shop.pss.dao.UserMapper;
import com.shop.pss.enums.ResultEnum;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 王瑞
 * @description
 * @date 2019-02-26 19:01
 */
@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User hr = userMapper.loadUserByUsername(s);
        if (hr == null) {
            throw new UsernameNotFoundException("用户名不对");
        }
        return hr;
    }

    public RestResult changePwd(String oldPwd, String confirmPwd) {

        try {
            //获取当前登录对象
            User principal = UserUtils.getCurrentHr();
            //获取用户加密后密码
            String pass = principal.getPassword();
            //比对原始密码
            BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
            //判断加密后密码一致性
            boolean flage = bcryptPasswordEncoder.matches(oldPwd, pass);
            if (flage) {
                // 密码加密后修改
                String hashPass = bcryptPasswordEncoder.encode(confirmPwd);
                // 修改密码
                User user = new User();
                user.setId(principal.getId());
                user.setPassword(hashPass);
                user.setEnabled(true);
                updateHr(user);
                return RestResult.success();
            } else {
                return RestResult.error(ResultEnum.PWD_UPDATE_UNKONOW);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestResult.error(ResultEnum.PWD_UPDATE_UNKONOW);
    }

    public int userReg(String username, String password) {
        //如果用户名存在，返回错误
        if (userMapper.loadUserByUsername(username) != null) {
            return -1;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(password);
        return userMapper.hrReg(username, encode);
    }

    // 注册学生用户
    @Transactional()
    public RestResult regStudentUser(User user){
        //如果用户名存在，返回错误
        if (userMapper.loadUserByUsername(user.getUsername()) != null) {
            return RestResult.error(ResultEnum.USER_IS_NOT_NULL);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userMapper.regStudent(user);
        Role role = roleMapper.studentRole();
        updateHrRoles(user.getId(), new Long[]{role.getId()});
        return RestResult.success();
    }

    public List<User> getHrsByKeywords(String keywords) {
        return userMapper.getHrsByKeywords(keywords);
    }

    public int updateHr(User user) {
        return userMapper.updateHr(user);
    }

    // 修改密码
    public RestResult updateUser(User user){
        // 密码加密后修改
        if (!Strings.isNullOrEmpty(user.getPassword())){
            BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
            String hashPass = bcryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(hashPass);
        }
        int result = updateHr(user);
        return result > 0 ? RestResult.success() : RestResult.error(ResultEnum.PWD_UPDATE_ERROR);
    }

    // 根据身份证号和用户名修改密码
    public RestResult retrievePwd(String idCard, String userName, String pwd) {
        User user = userMapper.getUserByIdCard(idCard, userName);
        if (user == null || user.getId() == null) {
            return RestResult.error(ResultEnum.USER_IS_NULL);
        }
        // 密码加密后修改
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashPass = bcryptPasswordEncoder.encode(pwd);
        user.setPassword(hashPass);
        user.setEnabled(true);
        int result = updateHr(user);
        return result > 0 ? RestResult.success() : RestResult.error(ResultEnum.PWD_UPDATE_ERROR);
    }

    public int updateHrRoles(Long hrId, Long[] rids) {
        int i = userMapper.deleteRoleByHrId(hrId);
        return userMapper.addRolesForHr(hrId, rids);
    }

    public User getHrById(Long hrId) {
        return userMapper.getHrById(hrId);
    }

    public int deleteHr(Long hrId) {
        return userMapper.deleteHr(hrId);
    }

    public List<User> getAllHrExceptAdmin() {
        return userMapper.getAllHr(UserUtils.getCurrentHr().getId());
    }

    public List<User> getAllHr() {
        return userMapper.getAllHr(null);
    }

    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }
}

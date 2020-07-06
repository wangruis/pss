package com.shop.pss.dao;

import com.shop.pss.bean.Role;
import com.shop.pss.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * @author 王瑞
 * @description
 * @date 2019-02-26 19:01
 */
@Mapper
public interface UserMapper {

    User loadUserByUsername(String username);

    List<Role> getRolesByHrId(Long id);

    int hrReg(@Param("username") String username, @Param("password") String password);

    int regStudent(User user);

    List<User> getHrsByKeywords(@Param("keywords") String keywords);

    int updateHr(User hr);

    int deleteRoleByHrId(Long hrId);

    int addRolesForHr(@Param("hrId") Long hrId, @Param("rids") Long[] rids);

    User getHrById(Long hrId);

    int deleteHr(Long hrId);

    List<User> getAllHr(@Param("currentId") Long currentId);

    List<User> getAllUser();

    User getUserByIdCard(@Param("idCard") String idCard, @Param("userName") String userName);
}

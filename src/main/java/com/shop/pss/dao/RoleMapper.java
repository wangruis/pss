package com.shop.pss.dao;

import com.shop.pss.bean.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * @author 王瑞
 * @description
 * @date 2019-02-26 19:01
 */
@Mapper
public interface RoleMapper {

    List<Role> roles();

    int addNewRole(@Param("role") String role, @Param("roleZh") String roleZh);

    int deleteRoleById(Long rid);

    Role studentRole();
}

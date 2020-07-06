package com.shop.pss.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 王瑞
 * @description
 * @date 2019-02-26 19:01
 */
@Mapper
public interface MenuRoleMapper {

    int deleteMenuByRid(@Param("rid") Long rid);

    int addMenu(@Param("rid") Long rid, @Param("mids") Long[] mids);
}

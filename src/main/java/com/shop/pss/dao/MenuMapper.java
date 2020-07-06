package com.shop.pss.dao;

import com.shop.pss.bean.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 王瑞
 * @description
 * @date 2019-02-26 19:01
 */
@Mapper
public interface MenuMapper {

    List<Menu> getAllMenu();

    List<Menu> getMenusByHrId(Long hrId);

    List<Menu> menuTree();

    List<Long> getMenusByRid(Long rid);
}

package com.shop.pss.dao;

import com.shop.pss.bean.ConsumeGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 王瑞
 * @description
 * @date 2019-02-26 19:45
 */
@Mapper
public interface ConsumeGoodsMapper {

    int insert(@Param("list") List<ConsumeGoods> list);

}

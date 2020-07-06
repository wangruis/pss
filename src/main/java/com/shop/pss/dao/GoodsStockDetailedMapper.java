package com.shop.pss.dao;

import com.shop.pss.bean.GoodsStockDetailed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 王瑞
 * @description
 * @date 2019-02-26 19:45
 */
@Mapper
public interface GoodsStockDetailedMapper {

    int insertOne(GoodsStockDetailed goodsStockDetailed);

    int insert(@Param("goodsStockDetailed") List<GoodsStockDetailed> goodsStockDetailed);

    List<GoodsStockDetailed> queryAll(Map map);

    List<Map> exportGoodsStockHistory(Map map);

    List<GoodsStockDetailed> queryOutGoods(Map map);

    List<GoodsStockDetailed> queryByGoodsCode(Map map);

}

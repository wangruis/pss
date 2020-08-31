package com.shop.pss.dao;

import com.shop.pss.bean.GoodsStock;
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
public interface GoodsStockMapper {

    int goodsShortageNum();

    GoodsStock queryOne(String code);

    List<GoodsStock> queryAllByIds(@Param("ids") List<Long> ids);

    List<GoodsStock> queryAllByCodes(@Param("codes") List<String> codes);

    List<GoodsStock> queryAllShortage();

    List<GoodsStock> queryAllGoodsStock(Map map);

    List<Map> goodsStockExport();

    int updateShortageNum(int num);

    int insertAll(@Param("goodsStocks") List<GoodsStock> goodsStocks);

    int update(GoodsStock goodsStock);

    int updateAll(@Param("goodsStocks") List<GoodsStock> goodsStocks);

    int delete(@Param("list") List<Long> list);


    List<Map> replenishment();

    List<Map> getAllGoods(@Param("date") String date);

    List<Map> getGoodsNum(@Param("date") String date);

}

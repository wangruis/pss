package com.shop.pss.dao;

import com.shop.pss.bean.ConsumeGoodsHistory;
import com.shop.pss.bean.PurchaseHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author 王瑞
 * @description
 * @date 2019-03-02 21:20
 */
@Mapper
public interface ConsumeGoodsHistoryMapper {

    List<PurchaseHistory> queryPurchase();

    List<PurchaseHistory> queryPurchaseHistory(Map param);

    List<Map> exportPurchaseHistory(Map param);

    int insert(ConsumeGoodsHistory consumeGoodsHistory);

}

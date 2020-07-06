package com.shop.pss.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.pss.common.RestResult;
import com.shop.pss.dao.ConsumeGoodsHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 王瑞
 * @description
 * @date 2019-03-02 21:20
 */
@Service
public class PurchaseMangerService {

    @Autowired
    private ConsumeGoodsHistoryMapper purchaseMapper;

    public RestResult queryPurchase(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        PageInfo pageInfo = new PageInfo(purchaseMapper.queryPurchase());
        return RestResult.success(pageInfo);
    }

    public RestResult queryPurchaseHistory(int pageNum, int pageSize, Map param){
        PageHelper.startPage(pageNum, pageSize);
        PageInfo pageInfo = new PageInfo(purchaseMapper.queryPurchaseHistory(param));
        return RestResult.success(pageInfo);
    }
}

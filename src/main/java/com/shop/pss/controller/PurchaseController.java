package com.shop.pss.controller;

import com.google.common.collect.Maps;
import com.shop.pss.bean.ConsumeScore;
import com.shop.pss.bean.GoodsStockDetailed;
import com.shop.pss.bean.ListValidateWrapper;
import com.shop.pss.common.OptionCommon;
import com.shop.pss.common.RestResult;
import com.shop.pss.enums.ResultEnum;
import com.shop.pss.service.GoodsStockManageService;
import com.shop.pss.service.PurchaseMangerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 王瑞
 * @description
 * @date 2019-02-26 19:44
 */

@RestController
@RequestMapping("/pss/api/consume")
public class PurchaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(PurchaseController.class);

    @Autowired
    private PurchaseMangerService purchaseMangerService;

    @Autowired
    private GoodsStockManageService goodsStockManageService;

    /**
     * @creator 王瑞
     * @createtime 2019/2/27 19:56
     * @description: 学生消费记录
     */
    @GetMapping("/query_purchase")
    public RestResult queryPurchase(
            @RequestParam(value = "pageNum", required = true, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = true, defaultValue = "10") int pageSize) {
        try {
            return purchaseMangerService.queryPurchase(pageNum, pageSize);
        } catch (Exception e) {
            LOGGER.error("学生消费记录查询失败，失败原因：{}", e.getMessage());
        }
        return RestResult.error();
    }


    /**
     * @creator 王瑞
     * @createtime 2019/2/27 19:56
     * @description: 学生消费记录历史查询
     */
    @GetMapping("/query_purchase_history")
    public RestResult queryPurchaseHistory(
            @RequestParam(value = "pageNum", required = true, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = true, defaultValue = "10") int pageSize,
            @RequestParam(value = "studentCardNo", required = false) String studentCardNo,
            @RequestParam(value = "goodsName", required = false) String goodsName,
            @RequestParam(value = "stuName", required = false) String stuName,
            @RequestParam(value = "start_date", required = false) String start_date,
            @RequestParam(value = "end_date", required = false) String end_date) {
        try {
            Map param = Maps.newHashMap();
            param.put("studentCardNo", studentCardNo);
            param.put("goodsName", goodsName);
            param.put("stuName", stuName);
            param.put("start_date", start_date);
            param.put("end_date", end_date);
            return purchaseMangerService.queryPurchaseHistory(pageNum, pageSize, param);
        } catch (Exception e) {
            LOGGER.error("学生消费记录历史查询失败，失败原因：{}", e.getMessage());
        }
        return RestResult.error();
    }

    /**
     * @creator 王瑞
     * @createtime 2019/2/27 19:56
     * @description: 学生剩余积分
     */
    @GetMapping("/query_score")
    public RestResult queryScore(@RequestParam(value = "cardNo", required = true) String cardNo) {
        try {
            ConsumeScore consumeScore = goodsStockManageService.availableScore(cardNo);
            if (consumeScore != null){
                return RestResult.success(consumeScore);
            }
            return RestResult.error(ResultEnum.CARD_UNKNOW);
        } catch (Exception e) {
            LOGGER.error("学生消费记录查询失败，失败原因：{}", e.getMessage());
        }
        return RestResult.error();
    }
}

package com.shop.pss.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.shop.pss.bean.GoodsStock;
import com.shop.pss.bean.GoodsStockDetailed;
import com.shop.pss.bean.ListValidateWrapper;
import com.shop.pss.common.OptionCommon;
import com.shop.pss.common.RestResult;
import com.shop.pss.dao.GoodsStockMapper;
import com.shop.pss.service.AsyncService;
import com.shop.pss.service.GoodsStockManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 王瑞
 * @description
 * @date 2019-02-26 19:44
 */

@RestController
@RequestMapping("/pss/api/stock")
public class GoodsStockController {
    private final static Logger LOGGER = LoggerFactory.getLogger(GoodsStockController.class);

    @Autowired
    private GoodsStockManageService goodsStockManageService;

    @Autowired
    private GoodsStockMapper goodsStockMapper;

    @Autowired
    private AsyncService asyncService;

    /**
     * @return
     * @creator 王瑞
     * @createtime 2019/2/27 19:56
     * @description: 查询缺货阀值数
     */
    @GetMapping("/goods_shortage_num")
    public RestResult goodsShortageNum() {
        try {
            return RestResult.success(goodsStockMapper.goodsShortageNum());
        } catch (Exception e) {
            LOGGER.error("查询缺货阀值数，失败原因：{}", e.getMessage());
        }
        return RestResult.error();
    }

    /**
     * @param shortageNum 缺货阀值数
     * @return
     * @creator 王瑞
     * @createtime 2019/2/27 19:56
     * @description: 设置缺货阀值数
     */
    @PostMapping("/set_shortage_num")
    public RestResult queryGoodsInfoOne(@RequestParam(value = "shortageNum", required = true) int shortageNum) {
        LOGGER.info("------ start request set_shortage_num shortageNum:{}", shortageNum);
        try {
            return RestResult.success(goodsStockMapper.updateShortageNum(shortageNum));
        } catch (Exception e) {
            LOGGER.error("设置缺货阀值数，失败原因：{}", e.getMessage());
        } finally {
            LOGGER.info("------ end request set_shortage_num param:{}", shortageNum);
        }
        return RestResult.error();
    }

    /**
     * @param code 商品条码
     * @return
     * @creator 王瑞
     * @createtime 2019/2/27 19:56
     * @description: 查询单条
     */
    @GetMapping("/goods_info")
    public RestResult queryGoodsInfoOne(@RequestParam(value = "code", required = true) String code) {
        LOGGER.info("------ start request goods_info code:{}", code);
        try {
            return goodsStockManageService.queryGoodsInfoOne(code);
        } catch (Exception e) {
            LOGGER.error("查询单条商品，失败原因：{}", e.getMessage());
        } finally {
            LOGGER.info("------ end request goods_info");
        }

        return RestResult.error();
    }

    @GetMapping("/query_goods")
    public RestResult queryAllGoodsStock(
            @RequestParam(value = "pageNum", required = true, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = true, defaultValue = "10") int pageSize,
            @RequestParam(value = "keyWord", required = false) String keyWord,
            @RequestParam(value = "shortage", required = false) Integer shortage) {
        LOGGER.info("------ start request query_goods pageNum:{}, pageSize:{}, keyWord:{}, shortage:{}",
                pageNum, pageSize, keyWord, shortage);
        try {
            return goodsStockManageService.queryAllGoodsStock(pageNum, pageSize, keyWord, shortage);
        } catch (Exception e) {
            LOGGER.error("分页查询商品，失败原因：{}", e.getMessage());
        } finally {
            LOGGER.info("------ end request query_goods");
        }
        return RestResult.error();
    }

    @GetMapping("/goods_stock_history")
    public RestResult goodsStockHistory(
            @RequestParam(value = "pageNum", required = true, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = true, defaultValue = "10") int pageSize,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "option", required = false) String option,
            @RequestParam(value = "keyWord", required = false) String keyWord,
            @RequestParam(value = "start_date", required = false) String start_date,
            @RequestParam(value = "end_date", required = false) String end_date) {
        try {
            Map param = Maps.newHashMap();
            param.put("code", code);
            param.put("option", option);
            param.put("keyWord", keyWord);
            param.put("start_date", start_date);
            param.put("end_date", end_date);
            LOGGER.info("------ start request goods_stock_history pageNum:{}, pageSize:{}, code:{}, option:{}, keyWord:{}, start_date:{}, end_date:{}",
                    pageNum, pageSize, code, option, keyWord, start_date, end_date);
            return goodsStockManageService.goodsStockHistory(pageNum, pageSize, param);
        } catch (Exception e) {
            LOGGER.error("出入库操作记录查询，失败原因：{}", e.getMessage());
        } finally {
            LOGGER.info("------ end request goods_stock_history");
        }
        return RestResult.error();
    }

    @GetMapping("/goods_out_history")
    public RestResult queryOutGoods(
            @RequestParam(value = "pageNum", required = true, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = true, defaultValue = "10") int pageSize,
            @RequestParam(value = "option", required = false) String option,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "start_date", required = false) String start_date,
            @RequestParam(value = "end_date", required = false) String end_date) {
        LOGGER.info("------ start request goods_out_history pageNum:{}, pageSize:{}, option:{}, type:{}, start_date:{}, end_date:{}",
                pageNum, pageSize, option, type, start_date, end_date);
        try {
            Map param = Maps.newHashMap();
            param.put("option", option);
            param.put("type", type);
            param.put("start_date", start_date);
            param.put("end_date", end_date);
            return goodsStockManageService.queryOutGoods(pageNum, pageSize, param);
        } catch (Exception e) {
            LOGGER.error("商品汇总查询，失败原因：{}", e.getMessage());
        } finally {
            LOGGER.info("------ end request goods_out_history");
        }
        return RestResult.error();
    }

    /**
     * @param goodsStockDetaileds
     * @return
     * @creator 王瑞
     * @createtime 2019/2/27 19:56
     * @description: 新增入库
     */
    @PostMapping("/put_stock")
    public synchronized RestResult putStock(@RequestBody @Validated ListValidateWrapper<GoodsStockDetailed> goodsStockDetaileds) {
        LOGGER.info("------ start request put_stock param:{}", JSON.toJSONString(goodsStockDetaileds));
        try {
            return goodsStockManageService.putStock(goodsStockDetaileds.getList(), OptionCommon.PUT_STOCK);
        } catch (Exception e) {
            LOGGER.error("入库失败，失败原因：{}", e.getMessage());
        } finally {
            LOGGER.info("------ end request put_stock");
        }
        return RestResult.error();
    }

    /**
     * @param goodsStock
     * @return
     * @creator 王瑞
     * @createtime 2019/2/27 19:56
     * @description: 修改库存商品
     */
    @PostMapping("/update_stock")
    public synchronized RestResult updateStock(@RequestBody @Validated GoodsStock goodsStock) {
        LOGGER.info("------ start request update_stock param:{}", JSON.toJSONString(goodsStock));
        try {
            return goodsStockManageService.updateStock(goodsStock);
        } catch (Exception e) {
            LOGGER.error("修改库存商品，失败原因：{}", e.getMessage());
        } finally {
            LOGGER.info("------ end request update_stock");
        }
        return RestResult.error();
    }

    /**
     * @param ids
     * @return
     * @creator 王瑞
     * @createtime 2019/2/27 19:56
     * @description: 删除库存商品
     */
    @PostMapping("/delete_stock")
    public synchronized RestResult deleteStock(@RequestBody List<Long> ids) {
        LOGGER.info("------ start request delete_stock params:{}", JSON.toJSONString(ids));
        try {
            return goodsStockManageService.deleteStock(ids);
        } catch (Exception e) {
            LOGGER.error("删除库存商品，失败原因：{}", e.getMessage());
        } finally {
            LOGGER.info("------ end request delete_stock");
        }
        return RestResult.error();
    }


    /**
     * @param goodsStockDetaileds
     * @return
     * @creator 王瑞
     * @createtime 2019/2/27 19:56
     * @description: 销售出库
     */
    @PostMapping("/out_stock")
    public synchronized RestResult outStock(
            @RequestParam(value = "studentCardNo", required = true) String studentCardNo,
            @RequestBody @Validated ListValidateWrapper<GoodsStockDetailed> goodsStockDetaileds) {
        LOGGER.info("------ start request out_stock studentCardNo:{}, param:{}", studentCardNo, JSON.toJSONString(goodsStockDetaileds));
        try {
            return goodsStockManageService.outStock(goodsStockDetaileds.getList(), studentCardNo);
        } catch (Exception e) {
            LOGGER.error("销售出库失败，失败原因：{}", e.getMessage());
        } finally {
            LOGGER.info("------ end request out_stock");
        }
        return RestResult.error();
    }

    @GetMapping("/shortage")
    public RestResult shortage() {
        asyncService.send(goodsStockMapper.queryAllShortage());
        return RestResult.success();
    }

}

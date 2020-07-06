package com.shop.pss.controller;

import com.google.common.collect.Maps;
import com.shop.pss.service.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 王瑞
 * @description
 * @date 2019-03-06 15:13
 */
@RestController
@RequestMapping("/pss/api/file")
public class FileUploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

    private static final String GOODS_STOCK = "goods_stock";
    private static final String PURCHASE = "purchase";
    private static final String GOODSSTOCKHISTORY = "goods_stock_history";


    @Autowired
    private FileUploadService fileUploadService;

    @GetMapping(value = "/goods_stock_export")
    public void goodsStockExport(HttpServletRequest request, HttpServletResponse response) {
        try {
            fileUploadService.exportExcel(null, request, response, GOODS_STOCK);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("商品信息导出失败：{}", e.getMessage());
        }
    }

    @GetMapping(value = "/purchase_history_export")
    public void purchaseHistoryExport(
            @RequestParam(value = "studentCardNo", required = false) String studentCardNo,
            @RequestParam(value = "goodsName", required = false) String goodsName,
            @RequestParam(value = "stuName", required = false) String stuName,
            @RequestParam(value = "start_date", required = false) String start_date,
            @RequestParam(value = "end_date", required = false) String end_date,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            Map map = Maps.newHashMap();
            map.put("studentCardNo", studentCardNo);
            map.put("goodsName", goodsName);
            map.put("stuName", stuName);
            map.put("start_date", start_date);
            map.put("end_date", end_date);
            fileUploadService.exportExcel(map, request, response, PURCHASE);
        } catch (Exception e) {
            LOGGER.error("学生消费记录信息导出失败：{}", e.getMessage());
        }
    }

    @GetMapping(value = "/stock_history_export")
    public void goodsStockHistoryExport(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "option", required = false) String option,
            @RequestParam(value = "keyWord", required = false) String keyWord,
            @RequestParam(value = "start_date", required = false) String start_date,
            @RequestParam(value = "end_date", required = false) String end_date,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            Map map = Maps.newHashMap();
            map.put("code", code);
            map.put("option", option);
            map.put("keyWord", keyWord);
            map.put("start_date", start_date);
            map.put("end_date", end_date);
            fileUploadService.exportExcel(map, request, response, GOODSSTOCKHISTORY);
        } catch (Exception e) {
            LOGGER.error("出入库记录信息导出失败：{}", e.getMessage());
        }
    }


}

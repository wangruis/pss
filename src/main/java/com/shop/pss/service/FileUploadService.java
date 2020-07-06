package com.shop.pss.service;

import com.shop.pss.bean.GoodsStock;
import com.shop.pss.dao.ConsumeGoodsHistoryMapper;
import com.shop.pss.dao.GoodsStockDetailedMapper;
import com.shop.pss.dao.GoodsStockMapper;
import com.shop.pss.excel.ExcelConfig;
import com.shop.pss.excel.ExcelConfigParser;
import com.shop.pss.excel.ExcelUtil;
import com.shop.pss.excel.model.ExcelFieldModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 王瑞
 * @description
 * @date 2019-03-06 15:15
 */
@Service
public class FileUploadService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadService.class);

    @Autowired
    private GoodsStockMapper goodsStockMapper;

    @Autowired
    private ConsumeGoodsHistoryMapper purchaseMapper;

    @Autowired
    private GoodsStockDetailedMapper goodsStockDetailedMapper;

    public void exportExcel(Map map, HttpServletRequest request, HttpServletResponse response, String type) throws Exception {
        List<String[]> list = new ArrayList<String[]>();

        String sheet = "Sheet1";
        List<Map> objc = null;
        if (type.equals("goods_stock"))
            objc = goodsStockMapper.goodsStockExport();
        else if (type.equals("purchase"))
            objc = purchaseMapper.exportPurchaseHistory(map);
        else if (type.equals("goods_stock_history"))
            objc = goodsStockDetailedMapper.exportGoodsStockHistory(map);
        //转换数据
        ExcelConfig excelConfig = ExcelConfigParser.getConfigById(type);
        String fileName = excelConfig.getConfigModel().getExportFileName();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String time = df.format(new Date());
        String title = fileName;
        fileName = fileName + time;
        String[] titles = null;
        List<Integer> checkItemLst = null;
        try {
            List<ExcelFieldModel> fields = excelConfig.getConfigModel().getFields();
            titles = new String[fields.size()];
            if (objc != null && objc.size() > 0) {
                for (int i = 0; i < objc.size(); i++) {
                    Map dos = objc.get(i);
                    String[] strs = new String[fields.size()];
                    checkItemLst = new ArrayList<Integer>();
                    for (int j = 0; j < fields.size(); j++) {
                        ExcelFieldModel field = fields.get(j);
                        titles[j] = (field.getShowName());
                        if (!field.isNullbale()) {
                            checkItemLst.add(j);
                        }
                        strs[j] = field.getExportData(dos);
                    }
                    list.add(strs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("导出失败！{}", e.getMessage());
        } finally {
            excelConfig.dispose();
        }

        try {
            sheet = title;
            new ExcelUtil()
                    .setSheet(sheet)
                    .setFilename(fileName)
                    .setTitle(title)
                    .setTitles(titles)
                    .setCheckItem(checkItemLst)
                    .setList(list).start(request, response, type);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("导出失败！{}", e.getMessage());
        }
    }


    /**
     * 将 JavaBean对象转化为 Map   此方法未测试
     *
     * @param bean 要转化的类型
     * @return Map对象
     * @author wyply115
     * @version 2016年3月20日 11:03:01
     */
    public static Map convertBean2Map(Object bean) throws Exception {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = beanInfo
                .getPropertyDescriptors();
        for (int i = 0, n = propertyDescriptors.length; i < n; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }


    /**
     * 将 List<JavaBean>对象转化为List<Map>
     *
     * @param type 要转化的类型
     * @param map
     * @return Object对象
     * @author wyply115
     * @version 2016年3月20日 11:03:01
     */
    public static List<Map<String, Object>> convertListBean2ListMap(List<GoodsStock> beanList) throws Exception {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (int i = 0, n = beanList.size(); i < n; i++) {
            Object bean = beanList.get(i);
            Map map = convertBean2Map(bean);
            mapList.add(map);
        }
        return mapList;
    }

}

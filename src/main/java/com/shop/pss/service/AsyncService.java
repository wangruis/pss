package com.shop.pss.service;

import com.alibaba.fastjson.JSON;
import com.shop.pss.bean.GoodsStock;
import com.shop.pss.bean.GoodsStockDetailed;
import com.shop.pss.common.RestResult;
import com.shop.pss.websocket.PssWebSocketHandler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author 王瑞
 * @description
 * @date 2019-04-04 16:18
 */
@Service
public class AsyncService {
    /**
     * 执行异步任务
     * 可以根据需求，自己加参数拟定，这里就做个测试演示
     */
    @Async("asyncServiceExecutor")
    public void send(List<GoodsStock> goodsStockList){
        if (goodsStockList != null && goodsStockList.size() > 0) {
            try {
                PssWebSocketHandler.sendInfo(JSON.toJSONString(goodsStockList));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

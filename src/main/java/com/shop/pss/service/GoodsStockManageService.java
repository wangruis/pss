package com.shop.pss.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.shop.pss.bean.*;
import com.shop.pss.common.IdGen;
import com.shop.pss.common.OptionCommon;
import com.shop.pss.common.RestResult;
import com.shop.pss.common.UserUtils;
import com.shop.pss.dao.*;
import com.shop.pss.enums.ResultEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author 王瑞
 * @description
 * @date 2019-02-26 20:38
 */
@Service
@Transactional
public class GoodsStockManageService {
    private final static Logger LOGGER = LoggerFactory.getLogger(GoodsStockManageService.class);

    @Autowired
    private ConsumeScoreMapper consumeScoreMapper;

    @Autowired
    private GoodsStockMapper goodsStockMapper;

    @Autowired
    private GoodsStockDetailedMapper goodsStockDetailedMapper;

    @Autowired
    private ConsumeGoodsMapper consumeGoodsMapper;

    @Autowired
    private ConsumeGoodsHistoryMapper consumeGoodsHistoryMapper;

    @Autowired
    private AsyncService asyncService;

    /**
     * @param code
     * @return
     * @creator 王瑞
     * @createtime 2019/3/2 21:07
     * @description: 查询商品
     */
    public RestResult queryGoodsInfoOne(String code) {
        return RestResult.success(goodsStockMapper.queryOne(code));
    }

    /**
     * @return
     * @creator 王瑞
     * @createtime 2019/3/4 17:30
     * @description: 展示所有库存商品
     */
    public RestResult queryAllGoodsStock(int pageNum, int pageSize, String keyWord, Integer shortage) {
        PageHelper.startPage(pageNum, pageSize);
        Map param = Maps.newHashMap();
        param.put("keyWord", keyWord);
        param.put("shortage", shortage);
        PageInfo pageInfo = new PageInfo(goodsStockMapper.queryAllGoodsStock(param));
        return RestResult.success(pageInfo);
    }

    public RestResult goodsStockHistory(int pageNum, int pageSize, Map param) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo pageInfo = new PageInfo(goodsStockDetailedMapper.queryAll(param));
        return RestResult.success(pageInfo);
    }

    public RestResult queryOutGoods(int pageNum, int pageSize, Map param) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo pageInfo = new PageInfo(goodsStockDetailedMapper.queryOutGoods(param));
        return RestResult.success(pageInfo);
    }

    /**
     * 入库新增操作
     *
     * @param stockDetailed
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RestResult putStock(List<GoodsStockDetailed> stockDetailed, String option) {
        Multimap<String, GoodsStockDetailed> multimap = ArrayListMultimap.create();
        for (GoodsStockDetailed detailed : stockDetailed) {
            multimap.put(detailed.getCode(), detailed);
        }
        Map<String, Collection<GoodsStockDetailed>> detailedMap = multimap.asMap();
        List<GoodsStock> updateGoodsStockList = Lists.newLinkedList();
        List<GoodsStock> insertGoodsStockList = Lists.newLinkedList();
        for (Map.Entry<String, Collection<GoodsStockDetailed>> detailed : detailedMap.entrySet()) {
            GoodsStock goodsStock = goodsStockMapper.queryOne(detailed.getKey());
            if (goodsStock != null && StringUtils.isNoneBlank(goodsStock.getCode())) {
                int num = 0;
                List<GoodsStockDetailed> stockDetaileds = (List<GoodsStockDetailed>) detailed.getValue();
                for (int i = 0; i < stockDetaileds.size(); i++) {
                    num += stockDetaileds.get(i).getNumber();
                    stockDetaileds.get(i).setOption(option);
                    stockDetaileds.get(i).setId(IdGen.idGen());
                    stockDetaileds.get(i).setStockTotal(goodsStock.getNumber());
                    stockDetaileds.get(i).setCreateBy(UserUtils.getCurrentHr().getUsername());
                }
                goodsStock.setNumber(goodsStock.getNumber() + num);
                goodsStock.setVersionId(goodsStock.getVersion() + 1L);
                updateGoodsStockList.add(goodsStock);
            } else {
                int num = 0;
                List<GoodsStockDetailed> stockDetaileds = (List<GoodsStockDetailed>) detailed.getValue();
                for (int i = 0; i < stockDetaileds.size(); i++) {
                    num += stockDetaileds.get(i).getNumber();
                    stockDetaileds.get(i).setOption(option);
                    stockDetaileds.get(i).setId(IdGen.idGen());
                    stockDetaileds.get(i).setStockTotal(0);
                    stockDetaileds.get(i).setCreateBy(UserUtils.getCurrentHr().getUsername());
                }
                goodsStock = new GoodsStock();
                goodsStock.setId(IdGen.idGen());
                goodsStock.setNumber(num);
                goodsStock.setCode(detailed.getKey());
                goodsStock.setName(stockDetaileds.get(0).getName());
                goodsStock.setPrice(stockDetaileds.get(0).getPrice());
                goodsStock.setScore(stockDetaileds.get(0).getScore());
                goodsStock.setUnit(stockDetaileds.get(0).getUnit());
                goodsStock.setShortage(stockDetaileds.get(0).getShortage());
                goodsStock.setVersion(1L);
                insertGoodsStockList.add(goodsStock);
            }
        }

        if (!insertGoodsStockList.isEmpty()) goodsStockMapper.insertAll(insertGoodsStockList);
        if (!updateGoodsStockList.isEmpty()) goodsStockMapper.updateAll(updateGoodsStockList);
        if (!stockDetailed.isEmpty()) goodsStockDetailedMapper.insert(stockDetailed);
        return RestResult.success();
    }

    /**
     * @param goodsStock
     * @return
     * @creator 王瑞
     * @createtime 2019/2/28 19:59
     * @description: 商品修改
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RestResult updateStock(GoodsStock goodsStock) {
        if (goodsStock.getScore() != null && goodsStock.getScore().compareTo(BigDecimal.ZERO) < 0) {
            return RestResult.error(ResultEnum.GOODS_SCORE);
        }
        if (goodsStock.getNumber() < 0) {
            return RestResult.error(ResultEnum.GOODS_NUM);
        }
        GoodsStock goodsStockQuery = goodsStockMapper.queryOne(goodsStock.getCode());
        if (goodsStockQuery == null) {
            return RestResult.error(ResultEnum.GOODS_NULL);
        }

        GoodsStockDetailed goodsStockDetailed = new GoodsStockDetailed();
        goodsStockDetailed.setId(IdGen.idGen());
        goodsStockDetailed.setStockTotal(goodsStockQuery.getNumber());
        goodsStockDetailed.setCreateBy(UserUtils.getCurrentHr().getUsername());
        if (StringUtils.isNoneBlank(goodsStock.getCode())) goodsStockDetailed.setCode(goodsStock.getCode());
        if (StringUtils.isNoneBlank(goodsStock.getName())) goodsStockDetailed.setName(goodsStock.getName());
        if (goodsStock.getScore() != null) goodsStockDetailed.setScore(goodsStock.getScore());
        if (goodsStockQuery.getNumber() > goodsStock.getNumber()) {
            // 修改出库
            int num = goodsStockQuery.getNumber() - goodsStock.getNumber();
            goodsStockDetailed.setOption(OptionCommon.UPDATE_OUT_STOCK);
            goodsStockDetailed.setNumber(num);
            goodsStockDetailedMapper.insertOne(goodsStockDetailed);
        } else if (goodsStockQuery.getNumber() < goodsStock.getNumber()) {
            // 修改入库
            int num = goodsStock.getNumber() - goodsStockQuery.getNumber();
            goodsStockDetailed.setOption(OptionCommon.UPDATE_PUT_STOCK);
            goodsStockDetailed.setNumber(num);
            goodsStockDetailedMapper.insertOne(goodsStockDetailed);
        }
        if (goodsStockQuery.getScore().compareTo(goodsStock.getScore()) != 0) {
            goodsStockDetailed.setId(IdGen.idGen());
            goodsStockDetailed.setNumber(0);
            goodsStockDetailed.setOption(OptionCommon.UPDATE_SCORE);
            goodsStockDetailedMapper.insertOne(goodsStockDetailed);
        }
        goodsStock.setVersion(goodsStockQuery.getVersion());
        if (goodsStockMapper.update(goodsStock) <= 0) {
            throw new RuntimeException();
        }
        return RestResult.success();
    }

    /**
     * @param ids
     * @return
     * @creator 王瑞
     * @createtime 2019/2/28 19:59
     * @description: 删除库存商品
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RestResult deleteStock(List<Long> ids) {

        List<GoodsStock> goodsStockList = goodsStockMapper.queryAllByIds(ids);

        if (goodsStockList != null && goodsStockList.size() > 0) {
            List<GoodsStockDetailed> goodsStockDetaileds = new LinkedList<>();
            for (GoodsStock goodsStock : goodsStockList) {
                GoodsStockDetailed goodsStockDetailed = new GoodsStockDetailed();
                goodsStockDetailed.setId(IdGen.idGen());
                goodsStockDetailed.setStockTotal(goodsStock.getNumber());
                goodsStockDetailed.setCode(goodsStock.getCode());
                goodsStockDetailed.setName(goodsStock.getName());
                goodsStockDetailed.setScore(goodsStock.getScore());
                goodsStockDetailed.setOption(OptionCommon.DELETE_STOCK);
                goodsStockDetailed.setNumber(goodsStock.getNumber());
                goodsStockDetailed.setCreateBy(UserUtils.getCurrentHr().getUsername());

                goodsStockDetaileds.add(goodsStockDetailed);
            }
            goodsStockDetailedMapper.insert(goodsStockDetaileds);
            goodsStockMapper.delete(ids);
            return RestResult.success();
        }
        return RestResult.error();
    }


    /**
     * 销售出库
     *
     * @param goodsStockDetaileds
     * @return
     * @creator 王瑞
     * @createtime 2019/3/1 9:35
     * @description:
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RestResult outStock(List<GoodsStockDetailed> goodsStockDetaileds, String studentCardNo) {
        // 学生剩余积分查询
        ConsumeScore consumeScore = availableScore(studentCardNo);
        if (null == consumeScore || Strings.isNullOrEmpty(consumeScore.getCardNo())) {
            return RestResult.error(ResultEnum.CARD_UNKNOW);
        }
        if (Strings.isNullOrEmpty(consumeScore.getId())
                || null == consumeScore.getAvailableScore()
                || consumeScore.getAvailableScore().compareTo(BigDecimal.ZERO) <= 0) {
            return RestResult.error(ResultEnum.SCORE_NULL);
        }
        goodsStockDetaileds = getNewList(goodsStockDetaileds);
        LOGGER.info("studentCardNo:{}, availableScore:{}", studentCardNo, consumeScore.getAvailableScore());
        // 消费记录库 销售出库库存出库记录 对应商品数量减少  关联
        // 出售商品 消费记录和商品关联bean
        List<ConsumeGoods> consumeGoodsList = new ArrayList<>();
        // 出售商品code
        List<GoodsStock> goodsStockList = new ArrayList<>();
        // 学生消费记录
        ConsumeGoodsHistory consumeGoodsHistory = new ConsumeGoodsHistory();
        // 缺货阀值
        List<GoodsStock> shortageList = new ArrayList<>();
        // 商品不足消息提醒
        StringBuilder sb = new StringBuilder();
        // 商品对应数量  key 商品code, value 商品数量

        consumeGoodsHistory.setId(IdGen.idGen());
        consumeGoodsHistory.setStudentCardNumber(studentCardNo);
        // 获取售出总积分
        BigDecimal score = new BigDecimal(0);
        for (GoodsStockDetailed goodsStockDetailed : goodsStockDetaileds) {
            goodsStockDetailed.setId(IdGen.idGen());
            goodsStockDetailed.setOption(OptionCommon.OUT_STOCK);
            goodsStockDetailed.setCreateBy(UserUtils.getCurrentHr().getUsername());

            ConsumeGoods consumeGoods = new ConsumeGoods();
            consumeGoods.setId(IdGen.idGen());
            consumeGoods.setConsumeHisId(consumeGoodsHistory.getId());
            consumeGoods.setGoodsId(goodsStockDetailed.getId());

            score = score.add(goodsStockDetailed.getScore());
            consumeGoodsList.add(consumeGoods);

            GoodsStock goodsStock = goodsStockMapper.queryOne(goodsStockDetailed.getCode());
            if (null != goodsStock && !Strings.isNullOrEmpty(goodsStock.getCode())) {
                goodsStockDetailed.setStockTotal(goodsStock.getNumber());
                int number = goodsStock.getNumber() - goodsStockDetailed.getNumber();
                LOGGER.info("goods name:{}, stock num:{}, sale num:{}", goodsStock.getName(), goodsStock.getNumber(), number);
                if (number < 0) {
                    sb.append("商品：" + goodsStock.getName() + "库存数量不足，现有库存：" + goodsStock.getNumber() + "\n");
                } else {
                    goodsStock.setNumber(number);
                    goodsStock.setVersionId(goodsStock.getVersion() + 1L);
                    if (goodsStock.getNumber() <= goodsStock.getShortage()) {
                        shortageList.add(goodsStock);
                    }
                }
                goodsStockList.add(goodsStock);
            } else {
                sb.append("商品：" + goodsStock.getName() + "不再库存中\n");
            }

        }

        LOGGER.info("goods out_stock totalScore :{}", score);
        // 积分余额判断
        if (consumeScore.getAvailableScore().compareTo(score) == -1) {
            return RestResult.error(ResultEnum.SCORE_DEFICIENCY, consumeScore.getAvailableScore());
        }

        if (null != sb && !Strings.isNullOrEmpty(sb.toString())) {
            return new RestResult("200044", sb.toString());
        }

        consumeGoodsHistory.setScore(score);
        goodsStockMapper.updateAll(goodsStockList);
        consumeGoodsMapper.insert(consumeGoodsList);

        consumeScoreMapper.updateScore(updateScore(consumeScore, score));
        consumeScoreMapper.insertRecord(insertRecord(consumeScore, score));

        consumeGoodsHistoryMapper.insert(consumeGoodsHistory);
        goodsStockDetailedMapper.insert(goodsStockDetaileds);

        asyncService.send(shortageList);
        return RestResult.success(consumeScore.getAvailableScore().subtract(score));
    }

    // 查询积分
    public ConsumeScore availableScore(String cardNo) {
        return consumeScoreMapper.queryAvailableScore(cardNo);
    }

    // 返回新集合
    public static List<GoodsStockDetailed> getNewList(List<GoodsStockDetailed> oldList) {
        HashMap<String, GoodsStockDetailed> tempMap = new HashMap<String, GoodsStockDetailed>();

        // 去掉重复的key
        for (GoodsStockDetailed goodsStockDetailed : oldList) {
            String code = goodsStockDetailed.getCode();
            // containsKey(Object key)该方法判断Map集合中是否包含指定的键名，如果包含返回true，不包含返回false
            // containsValue(Object value)该方法判断Map集合中是否包含指定的键值，如果包含返回true，不包含返回false
            if (tempMap.containsKey(code)) {
                // 合并相同key的value
                goodsStockDetailed.setNumber(tempMap.get(code).getNumber() + goodsStockDetailed.getNumber());
                goodsStockDetailed.setScore(tempMap.get(code).getScore().add(goodsStockDetailed.getScore()));
                // HashMap不允许key重复，当有key重复时，前面key对应的value值会被覆盖
                tempMap.put(code, goodsStockDetailed);
            } else {
                tempMap.put(code, goodsStockDetailed);
            }
        }
        List<GoodsStockDetailed> newList = new ArrayList<GoodsStockDetailed>();
        Iterator iter = tempMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            GoodsStockDetailed stockDetailed = (GoodsStockDetailed) entry.getValue();
            newList.add(stockDetailed);
        }
        return newList;
    }


    public ConsumeScore updateScore(ConsumeScore consumeScore, BigDecimal score) {
        ConsumeScore newConsumeScore = new ConsumeScore();
        newConsumeScore.setUsedScore(consumeScore.getUsedScore().add(score));
        newConsumeScore.setAvailableScore(consumeScore.getAvailableScore().subtract(score));
        newConsumeScore.setId(consumeScore.getId());
        return newConsumeScore;
    }

    public ConsumeScore insertRecord(ConsumeScore consumeScore, BigDecimal score) {
        ConsumeScore newConsumeScore = new ConsumeScore();
        newConsumeScore.setScore(score);
        newConsumeScore.setCardNo(consumeScore.getCardNo());
        newConsumeScore.setRegisterId(consumeScore.getRegisterId());
        newConsumeScore.setAvailableScore(consumeScore.getAvailableScore().subtract(score));
        return newConsumeScore;
    }


    public PageInfo replenishment(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        PageInfo pageInfo = new PageInfo(goodsStockMapper.replenishment());
        return pageInfo;
    }

    public List<Map> getAllGoods(String date){
        return goodsStockMapper.getAllGoods(date);

    }

    public List<Map> getGoodsNum(String date){
        return goodsStockMapper.getGoodsNum(date);

    }

}

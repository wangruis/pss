package com.shop.pss.bean;

import java.io.Serializable;

/**
 * @author 王瑞
 * @description
 * @date 2019-02-26 19:28
 */
public class ConsumeGoods implements Serializable {

    private Long id;

    private Long consumeHisId;

    private Long goodsId;

    /**
     * get id
     *
     * @return java.lang.Long id
     */
    public Long getId() {
        return id;
    }

    /**
     * set id
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * get consumeHisId
     *
     * @return java.lang.Long consumeHisId
     */
    public Long getConsumeHisId() {
        return consumeHisId;
    }

    /**
     * set consumeHisId
     *
     * @param consumeHisId
     */
    public void setConsumeHisId(Long consumeHisId) {
        this.consumeHisId = consumeHisId;
    }

    /**
     * get goodsId
     *
     * @return java.lang.Long goodsId
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * set goodsId
     *
     * @param goodsId
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}

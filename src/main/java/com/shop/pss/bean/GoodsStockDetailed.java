package com.shop.pss.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 王瑞
 * @description
 * @date 2019-02-26 19:01
 */
public class GoodsStockDetailed implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    // 商品名称
    @NotBlank(message="商品名称不能为空")
    private String name;

    // 商品名称
    @NotBlank(message="商品名条码不能为空")
    private String code;

    // 出入库方式 out_stock 出库；put_stock 入库；sell_out 售出；delete 删库
    private String option;

    @Min(value = 0, message = "阀值最小为0")
    private int shortage;

    // 商品数量
    @Min(value = 0, message = "商品数量最小为0")
    private int number;

    private int stockTotal;

    // 价格
    @Min(value = 0, message = "商品价格最小为0")
    private BigDecimal price;

    // 积分
    @Min(value = 0, message = "商品积分最小为0")
    private BigDecimal score;

    // 商品单位
    private String unit;

    // 创建者
    private String createBy;

    @JSONField(name = "create_date", format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date create_date;


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
     * get name
     *
     * @return java.lang.String name
     */
    public String getName() {
        return name;
    }

    /**
     * set name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get number
     *
     * @return int number
     */
    public int getNumber() {
        return number;
    }

    /**
     * set number
     *
     * @param number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * get code
     *
     * @return java.lang.String code
     */
    public String getCode() {
        return code;
    }

    /**
     * set code
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * get option
     *
     * @return java.lang.String option
     */
    public String getOption() {
        return option;
    }

    /**
     * set option
     *
     * @param option
     */
    public void setOption(String option) {
        this.option = option;
    }

    /**
     * get price
     *
     * @return java.math.BigDecimal price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * set price
     *
     * @param price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * get score
     *
     * @return java.math.BigDecimal score
     */
    public BigDecimal getScore() {
        return score;
    }

    /**
     * set score
     *
     * @param score
     */
    public void setScore(BigDecimal score) {
        this.score = score;
    }

    /**
     * get unit
     *
     * @return java.lang.String unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * set unit
     *
     * @param unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * get create_date
     *
     * @return java.util.Date create_date
     */
    public Date getCreate_date() {
        if (create_date == null) {
            return null;
        }
        return (Date) create_date.clone();
    }

    /**
     * set create_date
     *
     * @param create_date
     */
    public void setCreate_date(Date create_date) {
        if (create_date == null)
            this.create_date = null;
        else
            this.create_date = (Date) create_date.clone();
    }

    /**
     * get createBy
     *
     * @return java.lang.String createBy
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * set createBy
     *
     * @param createBy
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * get shortage
     *
     * @return int shortage
     */
    public int getShortage() {
        return shortage;
    }

    /**
     * set shortage
     *
     * @param shortage
     */
    public void setShortage(int shortage) {
        this.shortage = shortage;
    }

    /**
     * get stockTotal
     *
     * @return int stockTotal
     */
    public int getStockTotal() {
        return stockTotal;
    }

    /**
     * set stockTotal
     *
     * @param stockTotal
     */
    public void setStockTotal(int stockTotal) {
        this.stockTotal = stockTotal;
    }
}

package com.shop.pss.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 王瑞
 * @description
 * @date 2019-02-26 19:01
 */
public class GoodsStock implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    // 商品名称
    @NotBlank(message = "商品名称")
    private String name;

    // 商品条码
    @NotBlank(message = "商品条码不为空")
    private String code;

    // 商品数量
    @Min(value = 0, message = "商品数量最小为0")
    private int number;

    // 价格
    @Min(value = 0, message = "商品价格最小为0")
    private BigDecimal price;

    // 缺货提醒阀值
    @Min(value = 0, message = "阀值最小为0")
    private int shortage;

    // 积分
    @Min(value = 0, message = "商品积分最小为0")
    private BigDecimal score;

    // 商品单位
    private String unit;

    private Long version;

    private Long versionId;

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
     * get version
     *
     * @return java.lang.Long version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * set version
     *
     * @param version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * get versionId
     *
     * @return java.lang.Long versionId
     */
    public Long getVersionId() {
        return versionId;
    }

    /**
     * set versionId
     *
     * @param versionId
     */
    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }
}

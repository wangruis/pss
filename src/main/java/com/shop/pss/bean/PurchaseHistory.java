package com.shop.pss.bean;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 王瑞
 * @description
 * @date 2019-03-01 9:35
 */
public class PurchaseHistory {

    private String stuName;

    // 卡号
    private String studentCardNo;

    private String goodsName;

    private int number;

    private BigDecimal score;

    private String option;

    private String create_date;

    /**
     * get studentCardNo
     *
     * @return java.lang.String studentCardNo
     */
    public String getStudentCardNo() {
        return studentCardNo;
    }

    /**
     * set studentCardNo
     *
     * @param studentCardNo
     */
    public void setStudentCardNo(String studentCardNo) {
        this.studentCardNo = studentCardNo;
    }


    /**
     * get stuName
     *
     * @return java.lang.String stuName
     */
    public String getStuName() {
        return stuName;
    }

    /**
     * set stuName
     *
     * @param stuName
     */
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    /**
     * get goodsName
     *
     * @return java.lang.String goodsName
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * set goodsName
     *
     * @param goodsName
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
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
     * get create_date
     *
     * @return java.lang.String create_date
     */
    public String getCreate_date() {
        return create_date;
    }

    /**
     * set create_date
     *
     * @param create_date
     */
    public void setCreate_date(String create_date) {
        this.create_date = create_date;
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
}

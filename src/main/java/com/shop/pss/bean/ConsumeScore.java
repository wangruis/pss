package com.shop.pss.bean;

import java.math.BigDecimal;

/**
 * @author 王瑞
 * @description
 * @date 2019-03-09 16:46
 */
public class ConsumeScore {

    private String id;

    private BigDecimal score;

    private String stuName;

    private String registerId;

    private BigDecimal availableScore;

    private BigDecimal usedScore;

    private String cardNo;

    /**
     * get id
     *
     * @return java.lang.Long id
     */
    public String getId() {
        return id;
    }

    /**
     * set id
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * get score
     *
     * @return java.lang.String score
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
     * get registerId
     *
     * @return java.lang.Long registerId
     */
    public String getRegisterId() {
        return registerId;
    }

    /**
     * set registerId
     *
     * @param registerId
     */
    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    /**
     * get availableScore
     *
     * @return java.math.BigDecimal availableScore
     */
    public BigDecimal getAvailableScore() {
        return availableScore;
    }

    /**
     * set availableScore
     *
     * @param availableScore
     */
    public void setAvailableScore(BigDecimal availableScore) {
        this.availableScore = availableScore;
    }

    /**
     * get usedScore
     *
     * @return java.math.BigDecimal usedScore
     */
    public BigDecimal getUsedScore() {
        return usedScore;
    }

    /**
     * set usedScore
     *
     * @param usedScore
     */
    public void setUsedScore(BigDecimal usedScore) {
        this.usedScore = usedScore;
    }

    /**
     * get cardNo
     *
     * @return java.lang.String cardNo
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * set cardNo
     *
     * @param cardNo
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}

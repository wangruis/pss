package com.shop.pss.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 王瑞
 * @description
 * @date 2019-02-26 19:28
 */
public class ConsumeGoodsHistory implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String studentCardNumber;

    private BigDecimal score;

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
     * get studentCardNumber
     *
     * @return java.lang.String studentCardNumber
     */
    public String getStudentCardNumber() {
        return studentCardNumber;
    }

    /**
     * set studentCardNumber
     *
     * @param studentCardNumber
     */
    public void setStudentCardNumber(String studentCardNumber) {
        this.studentCardNumber = studentCardNumber;
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
     * get create_date
     *
     * @return java.util.Date create_date
     */
    public Date getCreate_date() {
        return create_date;
    }

    /**
     * set create_date
     *
     * @param create_date
     */
    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }
}

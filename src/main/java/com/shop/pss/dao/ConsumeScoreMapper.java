package com.shop.pss.dao;

import com.shop.pss.bean.ConsumeScore;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;


/**
 * @author 王瑞
 * @description
 * @date 2019-02-26 19:45
 */
@Mapper
public interface ConsumeScoreMapper {

    ConsumeScore queryAvailableScore(String cardNo);

    int insertRecord(ConsumeScore consumeScore);

    int updateScore(ConsumeScore consumeScore);
}

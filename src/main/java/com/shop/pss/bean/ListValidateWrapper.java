package com.shop.pss.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.List;


/**
 * @author 王瑞
 * @description
 * @date 2019-02-27 11:13
 */
public class ListValidateWrapper<T> {
    @Valid
    @NotEmpty
    private List<T> list;

    @JsonCreator
    public ListValidateWrapper(List<T> list){
        this.list = list;
    }
    @JsonValue
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}

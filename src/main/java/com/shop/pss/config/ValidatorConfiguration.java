package com.shop.pss.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @author 王瑞
 * @description
 * @date 2019-02-26 19:01
 */
@Configuration
public class ValidatorConfiguration {

    /**
     * @return
     * 
     * @creator 王瑞
     * @createtime 2019/2/26 19:07
     * @description: 
     */
    @Bean
    public Validator hibernateValidator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}

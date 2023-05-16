package com.example.readbyline.test.util;

import com.alibaba.excel.converters.AutoConverter;
import com.alibaba.excel.converters.Converter;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelAnno {

    boolean required() default true;

    String[] rules() default {""};

    Class<? extends Converter> converter() default AutoConverter.class;

}

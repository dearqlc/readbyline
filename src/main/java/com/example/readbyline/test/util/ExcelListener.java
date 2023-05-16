/**
 * Aliyun.comInc.
 * Copyright(c)2004-2021All Rights Reserved.
 */
package com.example.readbyline.test.util;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author 263425418814573784
 */
public class ExcelListener extends AnalysisEventListener<Object> {

    /**
     * 数据体
     */
    private List<Object> dataList = new ArrayList<>();

    /**
     * 这两条用来校验表头信息
     */
    private List<String> headList = new ArrayList();

    /**
     * 模板类
     */
    private Class classes;

    /**
     * 模板不匹配消息
     */
    private StringBuilder message = new StringBuilder();

    public ExcelListener() {
        super();
    }

    public ExcelListener(Class classes) {
        super();
        this.classes = classes;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(Object data, AnalysisContext context) {
        dataList.add(data);
    }

    /**
     * execl表头校验
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        if (classes == null) {
            return;
        }
        Set<String> propertySet = new HashSet<>();
        getClassProperty(classes, propertySet);
        for (Integer i : headMap.keySet()) {
            if (!propertySet.contains(headMap.get(i))) {
                int index = i + 1;
                message.append("表头第" + index + "列【" + headMap.get(i) + "】与模板不一致;");
            }
        }
        if (StringUtils.isNotBlank(message)) {
            throw new ExcelAnalysisException("解析excel出错，请传入正确格式的excel,错误详情：" + message);
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("所有数据解析完成！");
//        LOGGER_INFO.debug("所有数据解析完成！");
    }

    public List<Object> getDataList() {
        return dataList;
    }

    /**
     * 获取类属性注解
     *
     * @return
     */
    private void getClassProperty(Class classes, Set<String> propertySet) {
        //获取类所以属性
        Field[] fields = classes.getDeclaredFields();
        for (Field field : fields) {
            //获取ExcelProperty注解
            ExcelProperty property = field.getAnnotation(ExcelProperty.class);
            String[] value = property.value();
            for (String s : value) {
                propertySet.add(s);
            }
        }
        //获取父类属性
        Class superclass = classes.getSuperclass();
        if (classes.getSuperclass() != null) {
            getClassProperty(superclass, propertySet);
        }
    }
}
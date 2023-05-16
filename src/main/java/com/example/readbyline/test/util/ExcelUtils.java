package com.example.readbyline.test.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ExcelUtils {

    /**
     * 解析EXCEL 并根据注解完成必填校验及正则规则校验
     * 根据setResult操作设置校验结果
     * 未通过的会自动填写失败原因
     * 注意： 会清空所有上传时填写的结果和失败原因
     *
     * @param clazz     需解析成的对象
     * @param file      EXCEL文件
     * @param setResult 保存结果操作
     * @param setReason 保存失败原因操作
     * @param <T>       需解析成的对象类型
     * @return 解析结果列表
     */
    public static <T> List<T> readExcelWithCheck(Class<T> clazz, File file, Op setResult, Op setReason) {
        ExcelListener excelListener = new ExcelListener(clazz);
        EasyExcel.read(file, clazz, excelListener).sheet().doRead();
        List<Object> dataList = excelListener.getDataList();
        List<T> t = new ArrayList<>();

        Map<String, Prop> classProperty = getClassProperty(clazz);

        StringBuilder failReason = new StringBuilder("");
        for (Object o : dataList) {
            T data = (T) o;
            t.add(data);

            boolean isSuccess = true;
            failReason.delete(0, failReason.length());

            // 开始行校验
            for (String prop : classProperty.keySet()) {
                Prop p = classProperty.get(prop);

                Field f;
                try {
                    f = data.getClass().getDeclaredField(prop);
                    f.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                    isSuccess = false;
                    failReason.append("找不到【").append(p.getName()).append("】字段；");
                    continue;
                }

                Object value = null;
                try {
                    value = f.get(data);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                // 注解必填校验 且 无值
                if (p.getRequired() && value == null) {
                    // 该行数据导入失败， 必填项未填
                    failReason.append("【").append(p.getName()).append("]").append("字段必填；");
                    isSuccess = false;
                    continue;
                }

                // 非字符串不做规则校验
                if (!String.class.equals(f.getType())) {
                    continue;
                }
                String v = (String) value;
                // 规则校验  规则不为空且字段不为空
                if (p.getRules().length > 0 && StringUtils.isNotBlank(p.rules[0]) && StringUtils.isNotBlank(v)) {
                    boolean result = true;
                    for (String rule : p.rules) {
                        boolean matches = Pattern.matches(rule, v);
                        if (!matches) {
                            result = false;
                        }
                    }
                    // 存在未匹配的规则
                    if (!result) {
                        isSuccess = false;
                        failReason.append("【").append(p.getName()).append("】").append("字段不符合规则；");
                    }
                }

            }
            // 将结果保存至对象
            setRowResult(data, isSuccess, failReason.toString(), setResult, setReason);
        }
        return t;
    }

    private static <T> void setRowResult(T data, boolean isSuccess, String failReason, Op setResult, Op setReason) {
        if (!isSuccess) {
            setResult.op(data, "失败");
            setReason.op(data, failReason);
        }
    }

    /**
     * 获取Excel注解信息
     *
     * @param clazz 类
     * @return 属性名称、是否必填、校验正则
     */
    private static Map<String, Prop> getClassProperty(Class clazz) {
        Map<String, Prop> map = new HashMap<>();
        //获取类所以属性
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            //获取ExcelProperty注解
            ExcelAnno anno = field.getAnnotation(ExcelAnno.class);
            if (anno == null) {
                continue;
            }
            ExcelProperty property = field.getAnnotation(ExcelProperty.class);

            map.put(field.getName(), new Prop(anno.required(), anno.rules(), property.value()[0]));
        }
        return map;
    }

    @Data
    @AllArgsConstructor
    private static class Prop {
        private Boolean required;
        private String[] rules;
        private String name;
    }

}

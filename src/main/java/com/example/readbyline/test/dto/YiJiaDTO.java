package com.example.readbyline.test.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author :QLC
 * @Date :2023/5/15 18:30
 * @Description :
 */
@Data
public class YiJiaDTO {
    @ExcelProperty(value = "role_id")
    private String roleId;

    @ExcelProperty(value = "partner_name")
    private String partnerName;

    @ExcelProperty(value = "date_start")
    private String dateStart;

    @ExcelProperty(value = "date_end")
    private String dateEnd;

    @ExcelProperty(value = "org_code")
    private String orgCode;

    @ExcelProperty(value = "agreement_no")
    private String agreementNo;

    @ExcelProperty(value = "导入结果")
    private String result;

    @ExcelProperty(value = "失败原因")
    private String failReason;
}

package com.example.readbyline.test.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author QLC
 * @Date 2022/12/8 19:01
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolRequestDataDTO {
    /**
     * 机构
     */
    private String orgcd;
    /**
     * 合作伙伴代码
     */
    private String partnerCode;
    /**
     * 合作伙伴名称
     */
    private String partnerName;
    /**
     * 协议号
     */
    private String agentProtocolCode;
    /**
     * 服务代码
     */
    private String serviceCode;
    /**
     * 车俩数
     */
    private Integer carCount;
    /**
     * 生效日期
     */
    private String startDate;
    /**
     * 失效日期
     */
    private String endDate;
}

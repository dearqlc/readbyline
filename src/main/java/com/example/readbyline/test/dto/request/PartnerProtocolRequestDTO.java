package com.example.readbyline.test.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author QLC
 * @Date 2022/12/8 21:03
 * @Description
 */
@Data
@NoArgsConstructor
public class PartnerProtocolRequestDTO {
    /**
     * 过期时间
     */
    private String expiretime;
    /**
     * accessKey
     */
    private String accessKey;
    /**
     * 签名
     */
    private String signature;
    /**
     * 服务器IP地址
     */
    private String host;
    /**
     * 策略
     */
    private String policy;
    /**
     * data
     */
    private ProtocolRequestDataDTO data;

    public PartnerProtocolRequestDTO(String expiretime, String accessKey, String signature, String host, String policy) {
        this.expiretime = expiretime;
        this.accessKey = accessKey;
        this.signature = signature;
        this.host = host;
        this.policy = policy;
    }
}

package com.example.readbyline.test.dto.response;

import lombok.Data;

/**
 * @Author QLC
 * @Date 2022/12/8 20:56
 * @Description
 */
@Data
public class SignatureResponseDataDTO {
    /**
     * accessKey
     */
    private String accessKey;
    /**
     * 服务器IP地址
     */
    private String host;
    /**
     * 策略
     */
    private String policy;
    /**
     * 签名
     */
    private String signature;
    /**
     * 过期时间
     */
    private String expiretime;
}

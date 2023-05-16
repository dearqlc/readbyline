package com.example.readbyline;

import com.alibaba.fastjson.JSON;
import com.example.readbyline.test.constant.HttpConstant;
import com.example.readbyline.test.dto.YiJiaDTO;
import com.example.readbyline.test.dto.request.PartnerProtocolRequestDTO;
import com.example.readbyline.test.dto.request.ProtocolRequestDataDTO;
import com.example.readbyline.test.dto.request.SignatureRequestDTO;
import com.example.readbyline.test.dto.response.PartnerAgrSyncResponseDTO;
import com.example.readbyline.test.dto.response.SignatureResponseDTO;
import com.example.readbyline.test.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.List;

/**
 * @Author QLC
 * @Date 2022/12/27 11:01
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationStartup.class)
class ApplicationStartupTest {

    @Test
    public void syncAgreement() {
        // 获取Url
        String signatureUrl = HttpConstant.PREFIX + HttpConstant.PROD_URL + HttpConstant.SIGNATURE;
        String protocolUrl = HttpConstant.PREFIX + HttpConstant.PROD_URL + HttpConstant.PROTOCOL;
        // 获取restTemplate
        RestTemplate restTemplate = new RestTemplate();
        // 解析Excel
        String pathName = "E:\\xxx.xlsx";
        File file = new File(pathName);
        List<YiJiaDTO> list = ExcelUtils.readExcelWithCheck(YiJiaDTO.class, file,
                (t, value) -> ((YiJiaDTO) t).setResult(value),
                (t, value) -> ((YiJiaDTO) t).setFailReason(value)
        );
        for (YiJiaDTO yiJiaDTO : list) {
            // 获取签名
            SignatureRequestDTO signatureRequestDTO = new SignatureRequestDTO(100);
            ResponseEntity<SignatureResponseDTO> response = restTemplate.postForEntity(signatureUrl, signatureRequestDTO, SignatureResponseDTO.class);
            if (response.getBody() == null) {
                return;
            }
            // 填充签名数据
            PartnerProtocolRequestDTO protocolRequestDTO = new PartnerProtocolRequestDTO();
            protocolRequestDTO.setHost(response.getBody().getData().getHost());
            protocolRequestDTO.setPolicy(response.getBody().getData().getPolicy());
            protocolRequestDTO.setAccessKey(response.getBody().getData().getAccessKey());
            protocolRequestDTO.setSignature(response.getBody().getData().getSignature());
            protocolRequestDTO.setExpiretime(response.getBody().getData().getExpiretime());
            // 填充合约数据
            ProtocolRequestDataDTO data = new ProtocolRequestDataDTO();
            data.setCarCount(1);
            data.setOrgcd(yiJiaDTO.getOrgCode());
            data.setPartnerCode(yiJiaDTO.getRoleId());
            data.setPartnerName(yiJiaDTO.getPartnerName());
            data.setAgentProtocolCode(yiJiaDTO.getAgreementNo());
            data.setEndDate(yiJiaDTO.getDateEnd().substring(0, 10));
            data.setStartDate(yiJiaDTO.getDateStart().substring(0, 10));
            protocolRequestDTO.setData(data);
            // 同步到议价
            log.info("同步合作伙伴协议信息到议价平台, request: {}", JSON.toJSONString(protocolRequestDTO, true));
            ResponseEntity<PartnerAgrSyncResponseDTO> postForEntity = restTemplate.postForEntity(protocolUrl, protocolRequestDTO, PartnerAgrSyncResponseDTO.class);
            log.info("同步合作伙伴协议信息到议价平台, response:{}", JSON.toJSONString(postForEntity.getBody(), true));
        }
    }

}

package com.falser.cloud.parking.alipay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlipayConfig {

    @Value("alipay.config.appid")
    private String appid;
    // 私钥
    @Value("alipay.config.rsa_private_key")
    private String privateKey;
    // 异步回调地址
    @Value("alipay.config.notify_url")
    private String notifyUrl;
    // 同步回调地址
    @Value("alipay.config.return_url")
    private String returnUrl;
    // 请求网关地址
    @Value("alipay.config.gateway_url")
    private String serverUrl;
    // 编码
    @Value("alipay.config.charset")
    private String charset;
    // 返回格式
    @Value("alipay.config.format")
    private String format;
    // 支付宝公钥
    @Value("alipay.config.alipay_public_key")
    private String alipayPublicKey;
    // RSA2
    @Value("alipay.config.signtype")
    private String signType;
}

package com.falser.cloud.parking.alipay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeCancelRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AlipayTrade {

    @Value("${alipay.config.appid}")
    private String appid;
    // 私钥
    @Value("${alipay.config.rsa_private_key}")
    private String privateKey;
    // 请求网关地址
    @Value("${alipay.config.gateway_url}")
    private String serverUrl;
    // 编码
    @Value("${alipay.config.charset}")
    private String charset;
    // 返回格式
    @Value("${alipay.config.format}")
    private String format;
    // 支付宝公钥
    @Value("${alipay.config.alipay_public_key}")
    private String alipayPublicKey;
    // RSA2
    @Value("${alipay.config.signtype}")
    private String signType;

    public AlipayClient alipayClient() throws AlipayApiException {
        AlipayConfig alipayConfig = getAlipayConfig();
        return new DefaultAlipayClient(alipayConfig);
    }

    /**
     * 得到二维码
     *
     * @param model 模型
     * @return {@link AlipayTradePrecreateResponse}
     * @throws AlipayApiException 支付宝api例外
     */
    public AlipayTradePrecreateResponse getQrCode(AlipayTradePrecreateModel model) throws AlipayApiException {
        AlipayClient alipayClient = alipayClient();
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setBizModel(model);
        return alipayClient.execute(request);
    }

    private AlipayConfig getAlipayConfig() {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setAppId(appid);
        alipayConfig.setPrivateKey(privateKey);
        alipayConfig.setServerUrl(serverUrl);
        alipayConfig.setCharset(charset);
        alipayConfig.setFormat(format);
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setSignType(signType);
        return alipayConfig;
    }

    /**
     * 查询
     *
     * @param outTradeNo 贸易没有
     * @return {@link AlipayTradeQueryResponse}
     * @throws AlipayApiException 支付宝api例外
     */
    public AlipayTradeQueryResponse query(String outTradeNo) throws AlipayApiException {
        AlipayClient alipayClient = alipayClient();
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(outTradeNo);
        request.setBizModel(model);
        AlipayTradeQueryResponse queryResponse = null;
        try {
            queryResponse = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return queryResponse;
    }

    /**
     * 交易取消
     *
     * @throws AlipayApiException 支付宝api例外
     */
    public boolean tradeCancel(String outTradeNo) throws AlipayApiException {
        AlipayClient alipayClient = alipayClient();
        AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", outTradeNo);
        request.setBizContent(bizContent.toString());
        AlipayTradeCancelResponse response = alipayClient.execute(request);
        return response.isSuccess();
    }
}

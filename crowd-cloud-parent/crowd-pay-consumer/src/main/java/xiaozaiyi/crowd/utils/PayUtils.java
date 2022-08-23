package xiaozaiyi.crowd.utils;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import xiaozaiyi.crowd.config.AliPayResourceConfig;

/**
 * @author : Crazy_August
 * @description : 支付工具类
 * @Time: 2022-05-11   22:04
 */
public class PayUtils {

    public static AlipayTradePagePayResponse aliPay(AliPayResourceConfig aliPayResourceConfig,String orderNum,String amount,String subject) throws AlipayApiException {
        String gatewayUrl = aliPayResourceConfig.getGatewayUrl();
        String appId = aliPayResourceConfig.getAppId();
        String privateKey = aliPayResourceConfig.getMerchantPrivateKey();
        String alipayPublicKey = aliPayResourceConfig.getAlipayPublicKey();
        String format = aliPayResourceConfig.getFormat();
        String charset = aliPayResourceConfig.getCharset();
        String signType = aliPayResourceConfig.getSignType();
        String notifyUrl = aliPayResourceConfig.getNotifyUrl();
        String returnUrl = aliPayResourceConfig.getReturnUrl();

        //获得初始化的AlipayClient 实例
//        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
//        certAlipayRequest.setServerUrl(gatewayUrl);
//        certAlipayRequest.setAppId(appId);
//        certAlipayRequest.setPrivateKey(privateKey);
//        certAlipayRequest.setFormat(format);
//        certAlipayRequest.setCharset(charset);
//        certAlipayRequest.setSignType(signType);
//        certAlipayRequest.setCertPath(gatewayUrl);


        DefaultAlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appId,
                privateKey, format, charset, alipayPublicKey, signType);
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setNotifyUrl(notifyUrl);
        alipayRequest.setReturnUrl(returnUrl);
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderNum);
        bizContent.put("total_amount", amount);
        bizContent.put("subject", subject);
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        alipayRequest.setBizContent(bizContent.toJSONString());
        AlipayTradePagePayResponse alipayTradePagePayResponse = alipayClient.pageExecute(alipayRequest);
        return alipayTradePagePayResponse;
    }

}

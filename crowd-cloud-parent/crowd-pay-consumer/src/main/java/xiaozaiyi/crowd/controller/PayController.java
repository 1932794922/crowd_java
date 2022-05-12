package xiaozaiyi.crowd.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xiaozaiyi.crowd.config.AliPayResourceConfig;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-11   20:47
 */
@RestController
public class PayController {

    @Autowired
    private AliPayResourceConfig aliPayResourceConfig;

    @RequestMapping("/")
    public String pay() throws AlipayApiException {
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
        DefaultAlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appId,
                privateKey, format, charset, alipayPublicKey, signType);
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setNotifyUrl(notifyUrl);
        alipayRequest.setReturnUrl(returnUrl);
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", "20213211321301001");
        bizContent.put("total_amount", 5000);
        bizContent.put("subject", "Iphone6 16G");
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        alipayRequest.setBizContent(bizContent.toJSONString());
        AlipayTradePagePayResponse alipayTradePagePayResponse = alipayClient.pageExecute(alipayRequest);

        System.out.println(alipayTradePagePayResponse.getBody());
        return alipayTradePagePayResponse.getBody();
    }

}

package xiaozaiyi.crowd.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-11   16:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "aliyun.pay")
public class AliPayResourceConfig {

    private String appId;

    // 商户私钥，您的PKCS8格式RSA2私钥
    private String merchantPrivateKey;

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private String alipayPublicKey;

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
// public static String notifyUrl = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";
    private String notifyUrl;

    //    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问00000
//    public static String returnUrl = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";
    private String returnUrl;

    // 签名方式
//    public static String sign_type = "RSA2";
    private String signType;

    // 字符编码格式
//    public static String charset = "utf-8";
    private String charset;

    // 支付宝网关
//    public static String gateWayUrl = "https://openapi.alipay.com/gateway.do";
    private String gatewayUrl;

    // 日志路径
    private String logPath;

    private String format;

}

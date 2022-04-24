package xiaozaiyi.crowd.util;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponseBody;
import com.google.gson.Gson;
import com.sun.mail.util.MailSSLSocketFactory;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.extern.slf4j.Slf4j;
import xiaozaiyi.crowd.constant.CustomConstant;
import xiaozaiyi.crowd.exception.CustomException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

/**
 * 发送相关工具类
 *
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-24   13:07
 */
@Slf4j
public class SendUtil {

    /**
     * 发送验证码
     *
     * @param phoneNumbers 手机号
     * @param code         验证码
     * @return 响应字符串
     */
    public static String sendSmS(String phoneNumbers, String code) {

        // 指定 HttpClient，并配置 Http 请求参数
//        HttpClient httpClient = new ApacheAsyncHttpClientBuilder()
//                .connectionTimeout(Duration.ofSeconds(10)) // 设置连接超时时间，默认10秒
//                .responseTimeout(Duration.ofSeconds(10)) // 设置连接超时时间，默认20秒
//                .maxConnections(128) // 设置连接池大小
//                .maxIdleTimeOut(Duration.ofSeconds(50)) // 设置连接池超时时间，默认60秒
//                // 配置代理
//                .proxy(new ProxyOptions(ProxyOptions.Type.HTTP, new InetSocketAddress("<your-proxy-hostname>", 9001))
//                        .setCredentials("<your-proxy-username>", "<your-proxy-password>"))
//                // 如果是 https 连接，则需要配置证书，或者忽略证书 .ignoreSSL(true)
//                .x509TrustManagers(new X509TrustManager[]{})
//                .keyManagers(new KeyManager[]{})
//                .ignoreSSL(false)
//                .build();

        Properties properties = new Properties();
        properties.getProperty("configuration.properties");
        InputStream resourceAsStream = SendUtil.class.getClassLoader().getResourceAsStream("configuration.properties");
        // 解决中文乱码问题

        BufferedReader bf = null;
        if (resourceAsStream != null) {
            bf = new BufferedReader(new InputStreamReader(resourceAsStream));
        }

        try {
            properties.load(bf);
            String accessKeyId = properties.getProperty("aliyun.sms.accessKeyId");
            String accessKeySecret = properties.getProperty("aliyun.sms.accessKeySecret");
            String signName = properties.getProperty("aliyun.sms.signName");
            String templateCode = properties.getProperty("aliyun.sms.templateCode");

            // 配置 Credentials 认证信息，包括 ak, secret, token
            StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                    .accessKeyId(accessKeyId)
                    .accessKeySecret(accessKeySecret)
                    .build());

            // 配置产品 Client
            AsyncClient client = AsyncClient.builder()
                    .region("undefined") // 产品服务区域 ID
                    //.httpClient(httpClient) // 将配置好的 HttpClient 传入，不传则使用默认 HttpClient（Apache HttpClient）
                    .credentialsProvider(provider)
                    //.serviceConfiguration(Configuration.create()) // 产品服务级别配置，可不设
                    // Client 级别配置重写，可设置 RegionId、Endpoint、Http 请求参数等
                    .overrideConfiguration(
                            ClientOverrideConfiguration.create()
                                    .setEndpointOverride("dysmsapi.aliyuncs.com")
                            //.setReadTimeout(Duration.ofSeconds(30))
                    )
                    .build();
            String phoneCode = "{'code':" + code + "}";
            // Parameter settings for API request
            SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                    .signName(signName)
                    .templateCode(templateCode)
                    .phoneNumbers(phoneNumbers)
                    .templateParam(phoneCode)
                    // Request 级别配置重写，可设置 Http 请求参数等
                    //.requestConfiguration(RequestConfiguration.create().setHttpMethod(HttpMethod.GET))
                    .build();

            // 异步获取接口请求返回值
            CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);

            // 同步阻塞获取返回值方式
            SendSmsResponse resp = null;
            try {
                resp = response.get();
                SendSmsResponseBody body = resp.getBody();
                String jsonBody = new Gson().toJson(body);

                // 异步处理返回值方式
//        response.thenAccept(resp -> {
//            System.out.println(new Gson().toJson(resp.headers()));
//            System.out.println(new Gson().toJson(resp.body()));
//        }).exceptionally(throwable -> { // 处理异常
//            System.out.println(throwable.getMessage());
//            return null;
//        });
                return jsonBody;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                // Finally, close the client
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bf.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 发送QQ邮件
     *
     * @param subject 主题
     * @param text 内容
     * @param toQQEmail  发送的QQ邮箱号
     */
    public static void sendEmail(String subject, String text, String toQQEmail) {

        if ("".equals(toQQEmail.trim())) {
            log.info("发送失败{}", CustomConstant.EMAIL_IS_NULL);
            throw new CustomException(100, CustomConstant.EMAIL_IS_NULL);
        }

        Thread thread = new Thread(() -> {
            Properties properties = new Properties();
            MailSSLSocketFactory sf = null;
            try {
                sf = new MailSSLSocketFactory();
                sf.setTrustAllHosts(true);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
                log.info("发送失败{}", e.getMessage());
                throw new CustomException(100, CustomConstant.EMAIL_SEND_ERROR);
            }

//        exopwejwwkjjfajd
            properties.put("mail.transport.protocol", "smtp"); // 连接协议
            properties.put("mail.smtp.host", "smtp.qq.com"); // 主机名
            properties.put("mail.smtp.port", 465); // 端口号
            properties.put("mail.smtp.socketFactory.port", 465);

            properties.put("mail.smtp.auth", true);
            properties.put("mail.smtp.ssl.enable", true); // 设置是否使用ssl安全连接，一般都使用
            properties.put("mail.smtp.ssl.socketFactory", sf);
            properties.put("mail.debug", true); // 设置是否显示debug信息，true会在控制台显示相关信息

            properties.put("mail.user", "1932794922@qq.com");

            properties.put("mail.password", "tnehusygdkcaejbe"); //开启pop3/smtp时的验证码

            // 得到回话对象
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            properties.getProperty("mail.user"),
                            properties.getProperty("mail.password")); //发件人邮件用户名、授权码;
                }
            });

            session.setDebug(true);//代表启用debug模式，可以在控制台输出smtp协议应答的过程
            try {
                // 获取邮件对象
                Message message = new MimeMessage(session);

                // 设置发件人邮箱地址
                message.setFrom(new InternetAddress("1932794922@qq.com"));

                // 设置收件人邮箱地址
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(toQQEmail)); // 一个收件人

                // message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress("xxx@qq.com"), new InternetAddress("xxx@qq.com"), new InternetAddress("xxx@qq.com")}); // 多个收件人

                // 设置邮件标题
                message.setSubject(subject);
                // 设置邮件内容
                message.setText(text);
                Transport.send(message);
                log.info("邮件"+ toQQEmail +"发送成功");
            } catch (Exception e) {
                e.printStackTrace();
                log.info("发送失败{}", e.getMessage());
                throw new CustomException(100, CustomConstant.EMAIL_SEND_ERROR);
            }
        });

        thread.start();
    }
}

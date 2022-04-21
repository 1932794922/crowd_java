package xiaozaiyi.crowd.util;

import xiaozaiyi.crowd.constant.CustomConstant;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author : Crazy_August
 * @description :项目通用方法
 * @Time: 2022-03-31   15:35
 */
public class CustomUtils {

    /**
     * md5加密
     *
     * @param source 要加密字符串
     * @return 加密后字符串
     */
    public static String md5(String source) {
        // 1. 判断字符是否有效
        if (source == null || source.length() == 0) {
            // 2. 如果不是有效字符串抛出异常
            throw new RuntimeException(CustomConstant.MESSAGE_STRING_INVALIDATE);
        }
        try {
            // 3.获取MessageDigest
            String algorithm = "md5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            // 4. 获取明文的字节数组
            byte[] input = source.getBytes();
            // 5. 加密
            byte[] digest = messageDigest.digest(input);
            // 6.创建BigInteger
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, digest);
            // 7.按照16进制的值转换为字符串
            int radix = 16;
            return bigInteger.toString(radix).toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string   待渲染的字符串
     * @return null
     */
    public static void renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

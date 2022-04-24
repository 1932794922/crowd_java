package xiaozaiyi.crowd.util;


import org.junit.Test;

public class CrowedUtilsTest {

    @Test
    public void md5() {
        String password = "E10ADC3949BA59ABBE56E057F20F883E";
        String md5Password = CustomUtils.md5(password);
        System.out.println("md5Password = " + md5Password);
    }

    @Test
    public void sendSmSTest() {
        String s = SendUtil.sendSmS("19178851803", "12345");
        System.out.println(s);
    }

    @Test
    public void sendEmailTest() throws InterruptedException {
        SendUtil.sendEmail("登录验证码: ","验证码是" + "55555","849518004@qq.com");

    }
}
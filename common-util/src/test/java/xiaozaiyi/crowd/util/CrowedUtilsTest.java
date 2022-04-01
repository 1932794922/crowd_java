package xiaozaiyi.crowd.util;


import org.junit.Test;

public class CrowedUtilsTest {
    @Test
    public void md5() {
        String password = "E10ADC3949BA59ABBE56E057F20F883E";
        String md5Password = CrowedUtils.md5(password);
        System.out.println("md5Password = " + md5Password);
    }
}
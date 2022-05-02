package xiaozaiyi.crowd.util;


import org.junit.Test;

public class CrowedUtilsTest {
    @Test
    public void uploadResourceTest() {
        String path = "F:\\文档\\壁纸\\718310.png";
        String upload = QOssUploadUtil.uploadResource(path);
        System.out.println("upload = " + upload);
    }

    @Test
    public void downloadResourceeTest() {
        String path = "4-30/4f2950b0e82b486ead3e41afcc927dbc.png";
        String outputPath = "F:\\文档\\壁纸";
        QOssUploadUtil.downloadResource(path,outputPath);
    }

    @Test
    public void createBucketTest() {
        String bucketName = "augusaaaa-1306963922";
        QOssUploadUtil.createBucket(bucketName);
    }

    @Test
    public void deleteBucketTest() {
        String bucketName = "august-1306963922";
        QOssUploadUtil.removeBucket(bucketName);
    }

    @Test
    public void removeResourceTest() {
        String fileName = "/4-30/c8abcec0a9374a2f960f1015979b1da3.png";
        QOssUploadUtil.removeResource(fileName);
    }


    @Test
    public void md5() {
        String password = "E10ADC3949BA59ABBE56E057F20F883E";
        String md5Password = CustomUtils.md5(password);
        System.out.println("md5Password = " + md5Password);
    }

    @Test
    public void sendSmSTest() {
        SendUtil.sendSmS("19178851803");

    }

    @Test
    public void sendEmailTest() throws InterruptedException {
        SendUtil.sendEmail("登录验证码: ","验证码是" + "55555","849518004@qq.com");

    }
}
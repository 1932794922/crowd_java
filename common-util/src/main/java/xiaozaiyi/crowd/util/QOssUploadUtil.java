package xiaozaiyi.crowd.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import xiaozaiyi.crowd.constant.CustomConstant;
import xiaozaiyi.crowd.exception.CustomException;

import java.io.*;
import java.util.*;

/**
 * 腾讯云 oss 存储
 *
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-30   15:04
 */
@Data
public class QOssUploadUtil {

    private static String url;

    private static String bucketName;

    private static String secretId;

    private static String secretKey;

    private static String OSSArea;

    private static COSClient cosClient;

    static {
        Map<String, String> configuration = configuration();
        if (configuration != null) {
            url = configuration.get("url");
            bucketName = configuration.get("bucketName");
            secretId = configuration.get("secretId");
            secretKey = configuration.get("secretKey");
            OSSArea = configuration.get("OSSArea");
        }

    }

    /**
     * 上传文件
     *
     * @param file 上传的本地文件路径
     * @return 上传后的文件访问路径
     */
    public static String uploadResource(String file) {
        String fileName = null;
        PutObjectRequest putObjectRequest = null;
        try {
            COSClient cosClient = getCOSClient(secretId, secretKey, OSSArea);
            Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String folderName = month + "-" + day;
            String fileMainName = UUID.randomUUID().toString().replace("-", "");
            // 从原始文件名中获取文件扩展名
            String fileExtension = file.substring(file.lastIndexOf("."));
            // 文件名
            fileName = folderName + "/" + fileMainName + fileExtension;
            // 文件路径
            // 指定要上传的文件
            File localFile = new File(file);

            // 指定文件上传到 COS 上的路径，即对象键。例如对象键为 folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
            putObjectRequest = new PutObjectRequest(bucketName, fileName, localFile);

            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

            String eTag = putObjectResult.getETag();
            if (eTag == null) {
                throw new CustomException(100, CustomConstant.UPLOAD_FAILED);
            }
            cosClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(100, CustomConstant.UPLOAD_FAILED);
        } finally {
            cosClient.shutdown();
            if (putObjectRequest != null) {
                putObjectRequest.clone();
            }
        }
        // 返回文件的访问地址
        return url + "/" + fileName;
    }

    /**
     * 单文件上传
     *
     * @param file
     * @return
     */
    public static String uploadResource(MultipartFile file) {
        String fileName = null;
        PutObjectRequest putObjectRequest = null;
        COSClient cosClient = null;
        try {
            //由于MultipartFile文件内的内容为空的时候也判断为空，所以需要额外添加一个Objects.isNull(file)
            if (Objects.isNull(file)) {
                throw new CustomException(500, "未指定文件");
            }
            cosClient = getCOSClient(secretId, secretKey, OSSArea);
            Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String folderName = month + "-" + day;
            String fileMainName = UUID.randomUUID().toString().replace("-", "");

            // 从原始文件名中获取文件扩展名
            String fileExtension = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
            // 文件名
            fileName = folderName + "/" + fileMainName + fileExtension;
            // 文件路径
            // 指定要上传的文件
            InputStream inputStream = file.getInputStream();
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            String contentType = file.getContentType();
            objectMetadata.setContentType(contentType);

            // 指定文件上传到 COS 上的路径，即对象键。例如对象键为 folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
            putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata);

            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

            String eTag = putObjectResult.getETag();
            if (eTag == null) {
                throw new CustomException(100, CustomConstant.UPLOAD_FAILED);
            }
            // 返回文件的访问地址
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException(100, CustomConstant.UPLOAD_FAILED);
        } finally {
            if (cosClient != null) {
                cosClient.shutdown();
            }
            if (putObjectRequest != null) {
                putObjectRequest.clone();
            }
        }
        return url + "/" + fileName;
    }

    public static List<String> uploadResource(List<MultipartFile>  file) {
        String fileName = null;
        PutObjectRequest putObjectRequest = null;
        COSClient cosClient = null;
        List<String> urlList = new ArrayList<>();
        try {
            //由于MultipartFile文件内的内容为空的时候也判断为空，所以需要额外添加一个Objects.isNull(file)
            if (Objects.isNull(file)) {
                throw new CustomException(500, "未指定文件");
            }
            cosClient = getCOSClient(secretId, secretKey, OSSArea);
            Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String folderName = month + "-" + day;
            // 循环遍历
            for (MultipartFile multipartFile : file) {
                String fileMainName = UUID.randomUUID().toString().replace("-", "");
                // 从原始文件名中获取文件扩展名
                String fileExtension = Objects.requireNonNull(multipartFile
                                .getOriginalFilename())
                        .substring(multipartFile.getOriginalFilename().lastIndexOf("."));
                // 文件名
                fileName = folderName + "/" + fileMainName + fileExtension;
                // 文件路径
                // 指定要上传的文件
                InputStream inputStream = multipartFile.getInputStream();
                // 创建上传Object的Metadata
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentLength(inputStream.available());
                String contentType = multipartFile.getContentType();
                objectMetadata.setContentType(contentType);

                // 指定文件上传到 COS 上的路径，即对象键。例如对象键为 folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
                putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata);

                PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

                String eTag = putObjectResult.getETag();
                if (eTag == null) {
                    throw new CustomException(100, CustomConstant.UPLOAD_FAILED);
                }
                // 返回文件的访问地址
                urlList.add(url + "/" + fileName);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException(100, CustomConstant.UPLOAD_FAILED);
        } finally {
            if (cosClient != null) {
                cosClient.shutdown();
            }
            if (putObjectRequest != null) {
                putObjectRequest.clone();
            }
        }
        return urlList;
    }


    /**
     * 下载文件到本地
     *
     * @param key            文件名
     * @param outputFilePath 下载到本地的路径
     */
    public static void downloadResource(String key, String outputFilePath) {

        COSClient cosClient = getCOSClient(secretId, secretKey, OSSArea);

        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);

        COSObject cosObject = cosClient.getObject(getObjectRequest);
        COSObjectInputStream cosObjectInput = cosObject.getObjectContent();
        // 关闭输入流
        try {
            cosObjectInput.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(100, "下载失败");
        }
        String outputPath = outputFilePath + File.separator + key.substring(key.indexOf("/") + 1);
        File downFile = new File(outputPath);
        getObjectRequest = new GetObjectRequest(bucketName, key);
        ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);
        String eTag = downObjectMeta.getETag();
        if (eTag == null) {
            throw new CustomException(100, "下载失败");
        }
    }

    /**
     * 创建桶
     *
     * @param bucketName
     */
    public static void createBucket(String bucketName) {
        COSClient cosClient = getCOSClient(secretId, secretKey, "");
        // 创建存储空间
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
        // 设置 bucket 的权限为 Private(私有读写)、其他可选有 PublicRead（公有读私有写）、PublicReadWrite（公有读写）
        createBucketRequest.setCannedAcl(CannedAccessControlList.Private);
        try {
            Bucket bucketResult = cosClient.createBucket(createBucketRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(100, "创建存储空间失败");
        }
    }


    /**
     * 删除桶
     *
     * @param bucketName
     */
    public static void removeBucket(String bucketName) {
        COSClient cosClient = getCOSClient(secretId, secretKey, OSSArea);
        try {
            cosClient.listBuckets().forEach(System.out::println);
            cosClient.deleteBucket(bucketName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(100, "删除创建存储失败");
        }
    }


    /**
     * 删除文件
     *
     * @param fileName
     * @return
     */
    public static void removeResource(String fileName) {
        COSClient cosClient = getCOSClient(secretId, secretKey, OSSArea);
        try {
            cosClient.deleteObject(bucketName, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(100, "删除文件失败");
        }
    }


    /**
     * 读取配置文件
     *
     * @return 返回配置文件 Map
     */
    private static Map<String, String> configuration() {
        Map<String, String> configProps = new HashMap<>();
        Properties properties = new Properties();
        InputStream resourceAsStream = QOssUploadUtil.class.getClassLoader().getResourceAsStream("configuration.properties");
        // 解决中文乱码问题
        BufferedReader bf = null;
        if (resourceAsStream != null) {
            bf = new BufferedReader(new InputStreamReader(resourceAsStream));
        }
        try {
            properties.load(resourceAsStream);
            String secretId = properties.getProperty("qcloud.secretId");
            String secretKey = properties.getProperty("qcloud.secretKey");
            // 指定文件将要存放的存储桶
            String bucketName = properties.getProperty("qcloud.bucket.name");
            // 设置 bucket 的地域,
            String OSSArea = properties.getProperty("qcloud.oss.area");
            String url = properties.getProperty("qcloud.oss.url");
            configProps.put("url", url);
            configProps.put("secretId", secretId);
            configProps.put("secretKey", secretKey);
            configProps.put("bucketName", bucketName);
            configProps.put("OSSArea", OSSArea);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (bf != null) {
                    bf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                assert resourceAsStream != null;
                resourceAsStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return configProps;
    }

    /**
     * 返回 COSClient
     *
     * @return COSClient
     */
    private static COSClient getCOSClient(String secretId, String secretKey, String OSSArea) {
        COSClient cosClient;
        try {
            // 1 初始化用户身份信息（secretId, secretKey）。
            COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
            // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
            Region region = new Region(OSSArea);
            ClientConfig clientConfig = new ClientConfig(region);
            // 这里建议设置使用 https 协议
            clientConfig.setHttpProtocol(HttpProtocol.https);
            // 设置OSSClient允许打开的最大HTTP连接数，默认为1024个。
            clientConfig.setMaxConnectionsCount(1024);
            // 设置Socket层传输数据的超时时间，默认为50000毫秒。
            clientConfig.setSocketTimeout(50000);
            // 设置建立连接的超时时间，默认为50000毫秒。
            clientConfig.setConnectionTimeout(50000);
            // 设置从连接池中获取连接的超时时间（单位：毫秒），默认不超时。
            clientConfig.setConnectionRequestTimeout(1000);
            // 3 生成 cos 客户端。
            cosClient = new COSClient(cred, clientConfig);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(100, "获取cos客户端失败");
        }
        return cosClient;
    }

}
package xiaozaiyi.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-23   16:03
 */
@EnableDiscoveryClient
@SpringBootApplication
public class MysqlMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MysqlMainApplication.class, args);
    }
}

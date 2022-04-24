package xiaozaiyi.crowd;

import org.mybatis.spring.annotation.MapperScan;
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

@MapperScan(basePackages = "xiaozaiyi.crowd.mapper")
public class MysqlMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MysqlMainApplication.class, args);
    }
}

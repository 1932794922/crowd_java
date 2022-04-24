package xiaozaiyi.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * api
 *
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-23   19:42
 */
@SpringBootApplication(exclude= DataSourceAutoConfiguration.class)
@EnableFeignClients
public class ServiceApiMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceApiMainApplication.class, args);
    }
}

package xiaozaiyi.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-23   14:14
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaMainApplication.class, args);
    }
}

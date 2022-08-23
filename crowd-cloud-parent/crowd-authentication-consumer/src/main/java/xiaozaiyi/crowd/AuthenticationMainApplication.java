package xiaozaiyi.crowd;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-23   22:56
 */
@SpringBootApplication
public class AuthenticationMainApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(AuthenticationMainApplication.class, args);
//        String[] beanDefinitionNames = run.getBeanDefinitionNames();
//        System.out.println("===================================");
//        for (String beanDefinitionName : beanDefinitionNames) {
//            System.out.println(beanDefinitionName);
//        }
    }

}

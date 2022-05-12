package xiaozaiyi.crowd.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xiaozaiyi.crowd.PayMainApplication;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {PayMainApplication.class})
public class AliPayResourceTest {

    @Autowired
    private AliPayResourceConfig aliPayResource;

    @Test
    public void logResultTest() {
        System.out.println(aliPayResource);
    }

}
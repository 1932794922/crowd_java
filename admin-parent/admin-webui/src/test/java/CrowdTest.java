import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xiaozaiyi.crowd.entity.Admin;
import xiaozaiyi.crowd.mapper.AdminMapper;
import xiaozaiyi.crowd.service.AdminService;
import xiaozaiyi.crowd.util.ResultEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-03-27   20:25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:spring-mybatis-tx.xml"})
public class CrowdTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;


    @Test
    public void saveAdmin() {
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        Admin admin = new Admin(null, "tom", "123456", "xzy", "196@qq.com", "2022");
        adminService.saveAdmin(admin);
        ResultEntity data = ResultEntity.success().add("data", admin).add("xiaozai","aaaaa");
        logger.info("data" + admin);
        logger.info("adminResultEntity" + data);
    }

    @Test
    public void testLog() {
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        logger.debug("debug level");
        logger.info("info level");
        logger.warn("warn level");
        logger.error("error level");
    }


    @Test
    public void testMapper() {
        Admin admin = new Admin(null, "admin", "123456", "xiaozaiyi", "1932794922@qq.com", "2022-3-27");
        int insert = adminMapper.insert(admin);
        System.out.println(insert);
    }


    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}

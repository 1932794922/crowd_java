import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xiaozaiyi.crowd.entity.Admin;
import xiaozaiyi.crowd.entity.Role;
import xiaozaiyi.crowd.mapper.AdminMapper;
import xiaozaiyi.crowd.mapper.RoleMapper;
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

    @Autowired
    private RoleMapper roleMapper;

    private Logger logger =  LoggerFactory.getLogger(CrowdTest.class);

    @Test
    public void getPageInfoTest() {
        PageInfo<Admin> pageInfo = adminService.getPageInfo("", 2, 5);
        logger.info(pageInfo.toString());

    }


    @Test
    public void savaRoleTest() {
        for (int i = 0; i < 284; i++) {
            roleMapper.insert(new Role(null, "ROLE" + i));
        }
    }



    @Test
    public void saveAdmins() {
        String[] Surname = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "褚", "卫", "蒋", "沈", "韩", "杨", "朱", "秦", "尤", "许",

                "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜", "戚", "谢", "邹", "喻", "柏", "水", "窦", "章", "云", "苏", "潘", "葛", "奚", "范", "彭", "郎",

                "鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞", "任", "袁", "柳", "酆", "鲍", "史", "唐", "费", "廉", "岑", "薛", "雷", "贺", "倪", "汤", "滕", "殷",

                "罗", "毕", "郝", "邬", "安", "常", "乐", "于", "时", "傅", "皮", "卞", "齐", "康", "伍", "余", "元", "卜", "顾", "孟", "平", "黄", "和",

                "穆", "萧", "尹", "姚", "邵", "湛", "汪", "祁", "毛", "禹", "狄", "米", "贝", "明", "臧", "计", "伏", "成", "戴", "谈", "宋", "茅", "庞", "熊", "纪", "舒",

                "屈", "项", "祝", "董", "梁", "杜", "阮", "蓝", "闵", "席", "季"};
        Admin admin = new Admin();
        for (int i = 0; i < 288; i++) {
            int random = (int) (Math.random() * Surname.length);
            StringBuilder stringBuilder1 = new StringBuilder();
            for (int j = 0; j < 5; j++) {
                stringBuilder1.append((char) (Math.random() * 26 + 'a'));
            }
            long l = System.currentTimeMillis();
            admin.setLoginAcct(stringBuilder1.toString());
            if ((int) (Math.random() * 2 + 1) == 1) {
                admin.setUserName(Surname[random] + Surname[(int) (Math.random() * Surname.length)]);
            } else {
                admin.setUserName(Surname[random] + Surname[(int) (Math.random() * Surname.length)] + Surname[(int) (Math.random() * Surname.length)]);
            }
            admin.setUserPswd(stringBuilder1.toString());
            admin.setEmail(stringBuilder1 + "@163.com");
            admin.setCreateTime(l + "");
            adminMapper.insert(admin);
            System.out.println(admin);
        }
    }


    @Test
    public void saveAdmin() {
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        Admin admin = new Admin(null, "tom", "123456", "xzy", "196@qq.com", "2022");
        adminService.saveAdmin(admin);
        ResultEntity data = ResultEntity.success().add("data", admin).add("xiaozai", "aaaaa");
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

package xiaozaiyi.crowd.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xiaozaiyi.crowd.po.MemberPO;
import xiaozaiyi.crowd.vo.DetailProjectVO;
import xiaozaiyi.crowd.vo.PortalProjectVo;
import xiaozaiyi.crowd.vo.ProjectTypeVO;

import java.time.*;
import java.util.List;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Test
    public void memberMapperTest() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode("1523456");
        memberMapper.insert(new MemberPO("123456", password));
    }
    @Test
    public void projectMapperTest() {
        List<ProjectTypeVO> projectTypeVOS = projectMapper.selectProjectTypeVOList();
        List<PortalProjectVo> portalProjectVoList = projectTypeVOS.get(0).getPortalProjectVoList();

        log.info("portalProjectVoList = {}", portalProjectVoList);
    }

    @Test
    public void selectDetailProjectVOByProjectIdTest() {
        DetailProjectVO detailProjectVO = projectMapper.selectDetailProjectVOByProjectId(1);
        log.info("detailProjectVO = {}", detailProjectVO);
    }

    @Test
    public void betweenTime() {
        String startTime = "16519305963";
        Instant instant = Instant.ofEpochMilli(Long.parseLong(startTime));
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        LocalDate now = LocalDate.now();
        Period p = Period.between(LocalDate.from(localDateTime), now);
        System.out.printf("%d 日",  p.getDays());
    }
}

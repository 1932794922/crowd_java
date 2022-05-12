package xiaozaiyi.crowd.mapper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xiaozaiyi.crowd.mapper.ProjectMapper;
import xiaozaiyi.crowd.mapper.service.ProjectMapperService;
import xiaozaiyi.crowd.po.ProjectPO;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-03   15:36
 */
@Service
@Transactional(readOnly = true)
public class ProjectServiceMapperImpl extends ServiceImpl<ProjectMapper, ProjectPO> implements ProjectMapperService {

}

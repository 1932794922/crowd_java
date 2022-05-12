package xiaozaiyi.crowd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xiaozaiyi.crowd.po.ProjectPO;
import xiaozaiyi.crowd.vo.DetailProjectVO;
import xiaozaiyi.crowd.vo.PortalProjectVo;
import xiaozaiyi.crowd.vo.ProjectTypeVO;

import java.util.List;

@Mapper
public interface ProjectMapper extends BaseMapper<ProjectPO> {

    List<PortalProjectVo> selectProjectProjectVOList();

    List<ProjectTypeVO> selectProjectTypeVOList();

    DetailProjectVO selectDetailProjectVOByProjectId(Integer projectId);


}

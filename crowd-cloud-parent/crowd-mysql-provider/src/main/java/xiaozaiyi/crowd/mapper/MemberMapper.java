package xiaozaiyi.crowd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xiaozaiyi.crowd.po.MemberPO;

/**
 * 会员接口
 */

@Mapper

public interface MemberMapper extends BaseMapper<MemberPO> {


}

package xiaozaiyi.crowd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xiaozaiyi.crowd.po.OrderPO;

@Mapper
public interface OrderMapper extends BaseMapper<OrderPO> {
}

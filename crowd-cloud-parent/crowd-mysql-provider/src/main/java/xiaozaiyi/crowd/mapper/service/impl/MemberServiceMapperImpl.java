package xiaozaiyi.crowd.mapper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xiaozaiyi.crowd.mapper.MemberMapper;
import xiaozaiyi.crowd.mapper.service.MemberMapperService;
import xiaozaiyi.crowd.po.MemberPO;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-23   20:39
 */
@Transactional(readOnly = true)
@Service
// 这里要继承 ServiceImpl,里面已经实现了 baseMapper
public class MemberServiceMapperImpl extends ServiceImpl<MemberMapper, MemberPO> implements MemberMapperService {

}





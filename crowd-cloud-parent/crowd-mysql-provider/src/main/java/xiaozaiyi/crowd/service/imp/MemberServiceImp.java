package xiaozaiyi.crowd.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xiaozaiyi.crowd.mapper.MemberMapper;
import xiaozaiyi.crowd.po.MemberPO;
import xiaozaiyi.crowd.service.MemberService;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-23   20:39
 */
@Transactional(readOnly = true)
@Service
// 这里要继承 ServiceImpl,里面已经实现了 baseMapper
public class MemberServiceImp extends ServiceImpl<MemberMapper, MemberPO> implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public MemberPO getMemberPOByLoginAcct(String loginAcct) {
        LambdaQueryWrapper<MemberPO> memberPOLambdaQueryWrapper = new LambdaQueryWrapper<>();
        memberPOLambdaQueryWrapper.eq(MemberPO::getLoginAcct, loginAcct);
        MemberPO memberPO = memberMapper.selectOne(memberPOLambdaQueryWrapper);
        return memberPO;
    }
}





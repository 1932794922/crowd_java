package xiaozaiyi.crowd.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xiaozaiyi.crowd.constant.CustomConstant;
import xiaozaiyi.crowd.mapper.MemberMapper;
import xiaozaiyi.crowd.po.MemberPO;
import xiaozaiyi.crowd.service.MemberService;
import xiaozaiyi.crowd.util.api.R;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-23   20:39
 */
@Transactional(readOnly = true)
@Slf4j
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

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class, readOnly = false)
    public R<MemberPO> saveMemberPO(MemberPO memberPO) {

        int success = memberMapper.insert(memberPO);

        return R.status(success > 0);
    }

    @Override
    public R<MemberPO> memberLoginByAcctPassword(MemberPO memberPO) {
        String loginAcct = memberPO.getLoginAcct();

        // 通过账号查询用户信息
        MemberPO memberPOByLoginAcct = getMemberPOByLoginAcct(loginAcct);
        if (memberPOByLoginAcct == null) {
            return R.fail(CustomConstant.USERNAME_NOT_EXIST);
        }

        // 密码加密比对
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        String rawPassword = memberPO.getUserPassword();
        String encodePassword = memberPOByLoginAcct.getUserPassword();

        boolean matches = bCryptPasswordEncoder.matches(rawPassword, encodePassword);
        if (!matches){
            return R.fail(CustomConstant.PASSWORD_ERROR);
        }
        return R.data(memberPOByLoginAcct,CustomConstant.LOGIN_SUCCESS);
    }

}





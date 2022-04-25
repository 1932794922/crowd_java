package xiaozaiyi.crowd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xiaozaiyi.crowd.po.MemberPO;
import xiaozaiyi.crowd.util.api.R;

// 这里要继承 IService
public interface MemberService extends IService<MemberPO> {

    MemberPO getMemberPOByLoginAcct(String loginAcct);

    /**
     * 保存用户注销信息
     *
     * @return
     */
    R<MemberPO> saveMemberPO(MemberPO memberPO);

    /**
     * 通过账号和密码登录
     *
     * @param memberPO
     * @return
     */
    R<MemberPO> memberLoginByAcctPassword(MemberPO memberPO);
}

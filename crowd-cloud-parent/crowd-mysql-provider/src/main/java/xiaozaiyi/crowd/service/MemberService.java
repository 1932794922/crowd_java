package xiaozaiyi.crowd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xiaozaiyi.crowd.po.MemberPO;

// 这里要继承 IService
public interface MemberService extends IService<MemberPO> {


    MemberPO getMemberPOByLoginAcct(String loginAcct);
}

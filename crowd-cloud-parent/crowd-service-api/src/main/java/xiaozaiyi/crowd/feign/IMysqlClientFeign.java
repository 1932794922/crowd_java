package xiaozaiyi.crowd.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import xiaozaiyi.crowd.po.MemberPO;
import xiaozaiyi.crowd.util.api.R;

/**
 * 调用远程 mysql provider
 */
@FeignClient("august-mysql")
//浏览器请求到达这里
public interface IMysqlClientFeign {

    /**
     * 用户登录操作
     *
     * @param loginAcct
     * @return
     */
    @GetMapping("/client/member/login/acct")
    R<MemberPO> getMemberPOByLoginAcct(@RequestParam("loginAcct") String loginAcct);

    /**
     * 判断登录账号是否存在
     *
     * @param loginAcct
     * @return
     */
    @GetMapping("/client/member/acct/exist")
    R<MemberPO> getMemberPOByAcct(@RequestParam("loginAcct") String loginAcct);


    /**
     * 保存注册信息
     *
     * @param memberPO
     * @return
     */
    @PostMapping("/client/member/save")
    R<MemberPO> saveMemberPO(@RequestBody MemberPO memberPO);

    @PostMapping("/client/member/login")
    R<MemberPO> memberLoginByAcctPassword(@RequestBody MemberPO memberPO);
}

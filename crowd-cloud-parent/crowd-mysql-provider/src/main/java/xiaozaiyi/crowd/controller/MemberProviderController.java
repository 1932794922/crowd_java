package xiaozaiyi.crowd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xiaozaiyi.crowd.po.MemberPO;
import xiaozaiyi.crowd.service.MemberService;
import xiaozaiyi.crowd.util.api.R;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-23   20:34
 */
@RestController
public class MemberProviderController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/client/member/login/acct")
    public R<MemberPO> getMemberPOByLoginAcct(@RequestParam("loginAcct") String loginAcct) {
        MemberPO memberPO = memberService.getMemberPOByLoginAcct(loginAcct);
        return R.data(memberPO);
    }

}

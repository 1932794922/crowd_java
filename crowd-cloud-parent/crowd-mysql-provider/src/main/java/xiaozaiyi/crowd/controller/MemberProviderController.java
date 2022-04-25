package xiaozaiyi.crowd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xiaozaiyi.crowd.constant.CustomConstant;
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
        if (memberPO == null) {
            return R.fail(100, CustomConstant.ACCT_NOT_EXIST);
        }
        return R.data(memberPO);
    }

    /**
     *  绑定用户是否存在
     *
     * @param loginAcct
     * @return
     */
    @GetMapping("/client/member/acct/exist")
    public R<MemberPO> getMemberPOByAcct(@RequestParam("loginAcct") String loginAcct) {
        MemberPO memberPO = memberService.getMemberPOByLoginAcct(loginAcct);
        if (memberPO == null) {
            return R.status(true);
        }
        return R.fail(CustomConstant.ACCT_EXIST);
    }


    /**
     * 保存注册信息
     *
     * @param memberPO
     * @return
     */
    @PostMapping("/client/member/save")
    R<MemberPO> saveMemberPO(@RequestBody MemberPO memberPO) {
        R<MemberPO> memberPOR = memberService.saveMemberPO(memberPO);
        boolean success = memberPOR.isSuccess();
        return R.status(success);
    }

    @PostMapping("/client/member/login")
    R<MemberPO> memberLoginByAcctPassword(@RequestBody MemberPO memberPO){

        R<MemberPO> memberPOR = memberService.memberLoginByAcctPassword(memberPO);
        boolean success = memberPOR.isSuccess();
        return R.status(success,memberPOR.getMessage());
    }



}

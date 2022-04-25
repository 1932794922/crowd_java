package xiaozaiyi.crowd.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xiaozaiyi.crowd.service.MemberService;
import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.MemberVO;

/**
 * 会员主页控制器
 *
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-24   23:20
 */
@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;


    @GetMapping("/get/phone/code")
    public R<MemberVO> getPhoneCode(@RequestParam("phone") String phone) {
        R<MemberVO> memberVOR = memberService.getPhoneCode(phone);
        boolean success = memberVOR.isSuccess();
        return R.status(success, memberVOR.getMessage());
    }


    /**
     * 注册登录
     *
     * @param memberVO
     * @return
     */
    @PostMapping("/register")
    public R<MemberVO> memberRegister(@RequestBody MemberVO memberVO) {

        R<MemberVO> memberVOR = memberService.memberRegister(memberVO);
        log.info(memberVOR.toString());

        boolean success = memberVOR.isSuccess();

        if (!success) {
            // 注册失败
            return R.fail(memberVOR.getMessage());
        }
        return R.success(memberVOR.getMessage());
    }


    @PostMapping("/login")
    public R<MemberVO> memberLogin(@RequestBody MemberVO memberVO) {

        R<MemberVO> memberVOR = memberService.memberLogin(memberVO);
        boolean success = memberVOR.isSuccess();
        return R.status(success, memberVOR.getMessage());
    }

}

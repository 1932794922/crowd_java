package xiaozaiyi.crowd.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import xiaozaiyi.crowd.constant.CustomConstant;
import xiaozaiyi.crowd.service.MemberService;
import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.MemberVO;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

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
    public R<HashMap<String, String>> memberLogin(@RequestBody MemberVO memberVO) {

        R<MemberVO> memberVOR = memberService.memberLogin(memberVO);
        boolean success = memberVOR.isSuccess();
        if (!success) {
            return R.fail(memberVOR.getMessage());
        }
        HashMap<String, String> map = new HashMap<>();
        // 返回 token
        map.put("token", memberVOR.getData().getToken());
        return R.data(map);
    }

    @GetMapping("/logout")
    public R<MemberVO> memberLogout(HttpServletRequest request) {
        String authorizationToken = request.getHeader("authorization");
        String[] split = authorizationToken.split(" ");
        String token  = split[1];
        R<MemberVO> memberVOR  =  memberService.memberLogout(token);
        return R.status(memberVOR.isSuccess(), memberVOR.getMessage());
    }


    @GetMapping("/get/user")
    public R<HashMap<String, String>> getMemberUser(HttpServletRequest request) {
        String authorizationToken = request.getHeader("authorization");
        if (StringUtils.isEmpty(authorizationToken)) {
            return R.fail(CustomConstant.NULL_TOKEN);
        }
        String[] split = authorizationToken.split(" ");
        String token  = split[1];
        R<MemberVO> memberVOR  =  memberService.getMemberUser(token);
        boolean success = memberVOR.isSuccess();
        if (!success){
            R.fail(memberVOR.getMessage());
        }
        HashMap<String, String> map = new HashMap<>();
        // 返回 token
        map.put("userName", memberVOR.getData().getUserName());
        return R.data(map);
    }


}

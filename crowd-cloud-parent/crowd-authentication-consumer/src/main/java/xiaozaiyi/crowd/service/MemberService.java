package xiaozaiyi.crowd.service;

import xiaozaiyi.crowd.util.api.R;
import xiaozaiyi.crowd.vo.MemberVO;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-24   23:28
 */
public interface MemberService {

    /**
     * 会员注册
     *
     * @param memberVO
     * @return
     */
    R<MemberVO> memberRegister(MemberVO memberVO);

    /**
     * 获取验证码
     *
     * @param phone 手机号
     * @return 验证码
     */
    R<MemberVO> getPhoneCode(String phone);

    /**
     * 登录
     *
     * @param memberVO
     * @return
     */
    R<MemberVO> memberLogin(MemberVO memberVO);

    /**
     * 返回个人信息
     *
     * @param token
     * @return
     */
    R<MemberVO> getMemberUser(String token);

    /**
     * 退出登录
     *
     * @param token
     * @return
     */
    R<MemberVO> memberLogout(String token);
}

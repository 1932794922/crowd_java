package xiaozaiyi.crowd.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-25   10:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {

    private String loginAcct;

    private String userName;

    private String userPassword;

    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 验证码
     */
    private String code;

}

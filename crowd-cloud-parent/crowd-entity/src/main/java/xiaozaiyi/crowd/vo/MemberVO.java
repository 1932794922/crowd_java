package xiaozaiyi.crowd.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-04-25   10:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberVO implements Serializable {

    private static final long serialVersionUID = 1L;

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

    /**
     * 验证身份
     */
    private String token;

}

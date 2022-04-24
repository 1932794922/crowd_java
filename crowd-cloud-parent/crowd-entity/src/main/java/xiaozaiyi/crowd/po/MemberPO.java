package xiaozaiyi.crowd.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会员实体类
 * @author : Crazy_August
 * @description
 * @Time: 2022-04-23   17:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_member")
public class MemberPO {

    @TableId(type= IdType.AUTO)
    private Integer id;

    private String loginAcct;

    private String userName;

    private String userPassword;

    private String email;

    // '实名认证状态0 - 未实名认证， 1 - 实名认证申\r\n请中， 2 - 已实名认证',
    private Integer authStatus;

    // ' 0 - 个人， 1 - 企业',
    private Integer userType;

    private String realName;

    private String cardNum;

    // '0 - 企业， 1 - 个体， 2 - 个人， 3 - 政府'
    private Integer acctType;

    public MemberPO(String loginAcct, String userPassword) {
        this.loginAcct = loginAcct;
        this.userPassword = userPassword;
    }
}

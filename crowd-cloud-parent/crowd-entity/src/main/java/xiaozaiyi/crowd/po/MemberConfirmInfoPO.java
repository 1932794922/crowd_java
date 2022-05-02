package xiaozaiyi.crowd.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *  发起人确认信息
 *
 * @TableName t_member_confirm_info
 */
@TableName(value ="t_member_confirm_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberConfirmInfoPO implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 会员id
     */
    private Integer memberId;

    /**
     * 易付宝企业账号
     */
    private String payNum;

    /**
     * 法人身份证号
     */
    private String cardNum;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
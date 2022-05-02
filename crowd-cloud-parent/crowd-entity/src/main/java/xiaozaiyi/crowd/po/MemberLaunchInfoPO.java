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
 * 发起众筹项目的会员信息
 *
 *
 * @TableName t_member_launch_info
 */
@TableName(value ="t_member_launch_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberLaunchInfoPO implements Serializable {
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
     * 简单介绍
     */
    private String descriptionSimple;

    /**
     * 详细介绍
     */
    private String descriptionDetail;

    /**
     * 联系电话
     */
    private String phoneNum;

    /**
     * 客服电话
     */
    private String serviceNum;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
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
 * 
 * @TableName t_address
 */
@TableName(value ="t_address")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressPO implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 收件人
     */
    private String receiveName;

    /**
     * 手机号
     */
    private String phoneNum;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 用户 id
     */
    private Integer memberId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
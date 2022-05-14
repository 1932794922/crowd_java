package xiaozaiyi.crowd.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName t_address
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressVO {

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

}
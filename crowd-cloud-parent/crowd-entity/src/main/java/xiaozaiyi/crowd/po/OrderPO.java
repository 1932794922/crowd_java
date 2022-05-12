package xiaozaiyi.crowd.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 
 * @TableName t_order
 */
@TableName(value ="t_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPO implements Serializable {
    /**
     * 主键
     */
    @TableId
    private Integer id;

    /**
     * 订单号
     */
    private String orderNum;

    /**
     * 支付宝流水号
     */
    private String payOrderNum;

    /**
     * 订单金额
     */
    private Double orderAmount;

    /**
     * 是否开发票（0 不开，1 开）
     */
    private Integer invoice;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 订单备注
     */
    private String orderRemark;

    /**
     * 收货地址 id
     */
    private String addressId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
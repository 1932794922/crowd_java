package xiaozaiyi.crowd.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @TableName t_order_project
 */
@TableName(value ="t_order_project")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProjectPO implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 发起人
     */
    private String launchName;

    /**
     * 回报内容
     */
    private String returnContent;

    /**
     * 回报数量
     */
    private Integer returnCount;

    /**
     * 支持单价
     */
    private BigDecimal supportPrice;

    /**
     * 配送费用
     */
    private Integer freight;

    /**
     * 订单表的主键
     */
    private Integer orderId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
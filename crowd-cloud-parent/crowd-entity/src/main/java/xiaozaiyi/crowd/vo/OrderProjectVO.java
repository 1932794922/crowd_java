package xiaozaiyi.crowd.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-13   13:09
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProjectVO {

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
    private Integer supportPrice;

    /**
     * 配送费用
     */
    private Integer freight;

    /**
     * 订单表的主键
     */
    private Integer orderId;

    private Integer signalPurchase;

    private Integer purchase;

    private MemberLaunchInfoVO memberLaunchInfoVO;
}

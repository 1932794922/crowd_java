package xiaozaiyi.crowd.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailReturnVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	// 支持金额
	private BigDecimal supportMoney;
	
	// 回报内容介绍
	private String content;
	
	// 总回报数量，0为不限制
	private Integer count;
	
	// 是否限制单笔购买数量，0 表示不限购，1表示限购
	private Integer signalPurchase;
	
	// 如果单笔限购，那么具体的限购数量
	private Integer purchase;
	
	// 运费，“0”为包邮
	private Integer freight;

	// 众筹结束后返还回报物品天数
	private Integer returnDate;
}
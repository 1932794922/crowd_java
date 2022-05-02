package xiaozaiyi.crowd.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberLaunchInfoVO  implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	// 简单介绍
	private String descriptionSimple;
	
	// 详细介绍
	private String descriptionDetail;
	
	// 联系电话
	private String phoneNum;
	
	// 客服电话
	private String serviceNum;

}
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
public class MemberConfirmInfoVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 易付宝账号
	private String payNum;

	// 法人身份证号
	private String cardNum;
}
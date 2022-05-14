package xiaozaiyi.crowd.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-07   19:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailProjectVO {

    private Integer id;
    private String projectName;
    private String projectDesc;
    private String followerNum;
    private Integer status;
    private String money;
    private BigDecimal supportMoney;
    // 百分比
    private String percentage;
    private String deployDate;
    // 众筹天数
    private Integer day;
    private Integer lastDate;
    private Integer supportNum;
    private String headerPicturePath;
    private List<String> detailPicturePath;
    private List<DetailReturnVO> detailReturnVOList;

    private MemberLaunchInfoVO memberLaunchInfoVO;

}

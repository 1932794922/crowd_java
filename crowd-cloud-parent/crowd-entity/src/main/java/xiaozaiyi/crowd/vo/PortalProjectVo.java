package xiaozaiyi.crowd.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-04   22:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortalProjectVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer projectId;

    private String projectName;
    // 项目头图
    private String headerPicturePath;
    // 发布时间
    private String deployDate;
    // 项目进度
    private Integer completion;

    // 项目支持人数
    private Integer supporter;

    // 项目支持金额
    private BigDecimal money;


}

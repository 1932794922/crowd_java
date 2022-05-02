package xiaozaiyi.crowd.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-01   16:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectVO implements Serializable {

    private static final long serialVersionUID = 1L;
    // 分类id集合
    private List<Integer> typeIdList;

    // 标签id集合
    private List<Integer> tagIdList;

    // 项目名称
    private String projectName;

    // 项目描述
    private String projectDescription;

    // 计划筹集的金额
    private Integer money;

    // 筹集资金的天数
    private Integer day;

    // 创建项目的日期
    private String createDate;

    // 头图的路径
    private String headerPicturePath;

    // 详情图片的路径
    private List<String> detailPicturePathList;

    // 发起人信息
    private MemberLaunchInfoVO memberLaunchInfoVO;

    // 回报信息集合
    private List<ReturnVO> returnVOList;

    // 发起人确认信息
    private MemberConfirmInfoVO memberConfirmInfoVO;
}

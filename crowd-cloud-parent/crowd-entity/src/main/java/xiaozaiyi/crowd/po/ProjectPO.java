package xiaozaiyi.crowd.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 项目实体
 *
 * @TableName t_project
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="t_project")
public class ProjectPO implements Serializable {


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

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
     * 项目描述
     */
    private String projectDescription;

    /**
     * 筹集金额
     */
    private Long money;

    /**
     * 筹集天数
     */
    private Integer day;

    /**
     * 0-即将开始，1-众筹中，2-众筹成功，3-众筹失败

     */
    private Integer status;

    /**
     * 项目发起时间
     */
    private String deployDate;

    /**
     * 已筹集到的金额
     */
    private Long supportMoney;

    /**
     * 支持人数
     */
    private Integer supporter;

    /**
     * 百分比完成度
     */
    private Integer completion;

    /**
     * 发起人的会员id
     */
    private Integer memberId;

    /**
     * 项目创建时间
     */
    private String createDate;

    /**
     * 关注人数
     */
    private Integer follower;

    /**
     * 头图路径
     */
    private String headerPicturePath;


}
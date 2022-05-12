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
 * 
 * @TableName t_project_item_pic
 */
@TableName(value ="t_project_item_pic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectItemPicPO implements Serializable {


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 图片路径
     */
    private String itemPicPath;

}
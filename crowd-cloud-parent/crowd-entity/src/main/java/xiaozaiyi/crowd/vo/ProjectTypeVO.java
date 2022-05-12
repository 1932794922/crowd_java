package xiaozaiyi.crowd.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-04   22:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTypeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String remark;

    private List<PortalProjectVo> portalProjectVoList;

}

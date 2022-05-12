package xiaozaiyi.crowd.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目PO其他的PO的关系
 *
 * @author : Crazy_August
 * @description :
 * @Time: 2022-05-03   17:41
 */
@Mapper
public interface ProjectRelationShipMapper {

    /**
     * 保存项目、分类的关联关系信息
     *
     * @param typeIdList 分类id集合
     * @param projectId  项目id
     * @return
     */
    int insertRelationShipProjectWithType(@Param("typeIdList") List<Integer> typeIdList, @Param("projectId") Integer projectId);

    int insertRelationShipProjectWithTag(@Param("tagIdList") List<Integer> tagIdList, @Param("projectId") Integer projectId);

    int insertRelationShipProjectWithDetailImage(@Param("detailImageUrlList") List<String> detailImageUrlList,@Param("projectId") Integer projectId);

//    int insertRelationShipProjectWithReturn(@Param("returnVOList") List<ReturnPO> returnPOList, @Param("projectId") Integer projectId);

}

package xiaozaiyi.crowd.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xiaozaiyi.crowd.entity.Role;
import xiaozaiyi.crowd.entity.RoleExample;

public interface RoleMapper {
    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectRoleByKeyWord(String keywords);

    int batchDeleteByIds(List<Integer> ids);

    List<Role> selectNoAssignRoleByAdminId(Integer id);

    List<Role> selectAssignRoleByAdminId(Integer id);
}
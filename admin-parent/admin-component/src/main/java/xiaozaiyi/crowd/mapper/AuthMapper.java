package xiaozaiyi.crowd.mapper;

import org.apache.ibatis.annotations.Param;
import xiaozaiyi.crowd.entity.Auth;
import xiaozaiyi.crowd.entity.AuthExample;

import java.util.List;

public interface AuthMapper {
    long countByExample(AuthExample example);

    int deleteByExample(AuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Auth record);

    int insertSelective(Auth record);

    List<Auth> selectByExample(AuthExample example);

    Auth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByExample(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

    List<Auth> selectNoAssignAuthyRoleId(Integer id);

    List<Auth> selectAssignAuthByRoleId(Integer id);

    int deleteAuthRoleRelationsShip(Integer id);

    int insertAuthRoleRelationsShip(@Param("id") Integer id,@Param("ids") List<Integer> ids);
}
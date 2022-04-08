package xiaozaiyi.crowd.mapper;

import org.apache.ibatis.annotations.Param;

import xiaozaiyi.crowd.entity.Admin;
import xiaozaiyi.crowd.entity.AdminExample;

import java.util.List;

public interface AdminMapper {

    long countByExample(AdminExample example);

    int deleteByExample(AdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    List<Admin> selectByExample(AdminExample example);

    Admin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByExample(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    List<Admin> selectAdminByKeyWord(String keyWord);

    int batchDeleteByIds(List<Integer> ids);

    /**
     * 批量插入管理员和角色的关系
     * @param id
     * @param ids
     * @return
     */
    int insertAdminRoleRelationsShip(@Param("id") Integer id,@Param("ids") List<Integer> ids);

    /**
     * 删除该管理员的所有角色
     * @param id
     */
    int deleteAdminRoleRelationsShip(Integer id);
}
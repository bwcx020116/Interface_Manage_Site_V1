package iie.ac.cn.site.dao;

import iie.ac.cn.site.model.RolePermit;
import iie.ac.cn.site.model.RolePermitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolePermitMapper {
    long countByExample(RolePermitExample example);

    int deleteByExample(RolePermitExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RolePermit record);

    int insertSelective(RolePermit record);

    List<RolePermit> selectByExample(RolePermitExample example);

    RolePermit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RolePermit record, @Param("example") RolePermitExample example);

    int updateByExample(@Param("record") RolePermit record, @Param("example") RolePermitExample example);

    int updateByPrimaryKeySelective(RolePermit record);

    int updateByPrimaryKey(RolePermit record);
}
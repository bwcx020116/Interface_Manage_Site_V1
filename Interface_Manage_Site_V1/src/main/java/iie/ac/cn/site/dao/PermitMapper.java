package iie.ac.cn.site.dao;

import iie.ac.cn.site.model.Permit;
import iie.ac.cn.site.model.PermitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PermitMapper {
    long countByExample(PermitExample example);

    int deleteByExample(PermitExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Permit record);

    int insertSelective(Permit record);

    List<Permit> selectByExample(PermitExample example);

    Permit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Permit record, @Param("example") PermitExample example);

    int updateByExample(@Param("record") Permit record, @Param("example") PermitExample example);

    int updateByPrimaryKeySelective(Permit record);

    int updateByPrimaryKey(Permit record);
}
package iie.ac.cn.site.dao;

import iie.ac.cn.site.model.Metadata;
import iie.ac.cn.site.model.MetadataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MetadataMapper {
    long countByExample(MetadataExample example);

    int deleteByExample(MetadataExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Metadata record);

    int insertSelective(Metadata record);

    List<Metadata> selectByExample(MetadataExample example);

    Metadata selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Metadata record, @Param("example") MetadataExample example);

    int updateByExample(@Param("record") Metadata record, @Param("example") MetadataExample example);

    int updateByPrimaryKeySelective(Metadata record);

    int updateByPrimaryKey(Metadata record);
}
package iie.ac.cn.site.dao;

import iie.ac.cn.site.model.ReveapiMeta;
import iie.ac.cn.site.model.ReveapiMetaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReveapiMetaMapper {
    long countByExample(ReveapiMetaExample example);

    int deleteByExample(ReveapiMetaExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ReveapiMeta record);

    int insertSelective(ReveapiMeta record);

    List<ReveapiMeta> selectByExample(ReveapiMetaExample example);

    ReveapiMeta selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ReveapiMeta record, @Param("example") ReveapiMetaExample example);

    int updateByExample(@Param("record") ReveapiMeta record, @Param("example") ReveapiMetaExample example);

    int updateByPrimaryKeySelective(ReveapiMeta record);

    int updateByPrimaryKey(ReveapiMeta record);
}
package iie.ac.cn.site.dao;

import iie.ac.cn.site.model.ApiTemplate;
import iie.ac.cn.site.model.ApiTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ApiTemplateMapper {
    long countByExample(ApiTemplateExample example);

    int deleteByExample(ApiTemplateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApiTemplate record);

    int insertSelective(ApiTemplate record);

    List<ApiTemplate> selectByExample(ApiTemplateExample example);

    ApiTemplate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApiTemplate record, @Param("example") ApiTemplateExample example);

    int updateByExample(@Param("record") ApiTemplate record, @Param("example") ApiTemplateExample example);

    int updateByPrimaryKeySelective(ApiTemplate record);

    int updateByPrimaryKey(ApiTemplate record);
}
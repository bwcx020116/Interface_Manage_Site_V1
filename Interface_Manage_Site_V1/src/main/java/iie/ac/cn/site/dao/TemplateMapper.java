package iie.ac.cn.site.dao;

import iie.ac.cn.site.model.Template;
import iie.ac.cn.site.model.TemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TemplateMapper {
    long countByExample(TemplateExample example);

    int deleteByExample(TemplateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Template record);

    int insertSelective(Template record);

    List<Template> selectByExample(TemplateExample example);

    Template selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Template record, @Param("example") TemplateExample example);

    int updateByExample(@Param("record") Template record, @Param("example") TemplateExample example);

    int updateByPrimaryKeySelective(Template record);

    int updateByPrimaryKey(Template record);
}
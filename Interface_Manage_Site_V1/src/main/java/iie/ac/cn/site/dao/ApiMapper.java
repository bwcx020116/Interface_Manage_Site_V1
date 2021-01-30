package iie.ac.cn.site.dao;

import iie.ac.cn.site.model.Api;
import iie.ac.cn.site.model.ApiExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ApiMapper {
    long countByExample(ApiExample example);

    int deleteByExample(ApiExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Api record);

    int insertSelective(Api record);

    List<Api> selectByExample(ApiExample example);

    Api selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Api record, @Param("example") ApiExample example);

    int updateByExample(@Param("record") Api record, @Param("example") ApiExample example);

    int updateByPrimaryKeySelective(Api record);

    int updateByPrimaryKey(Api record);
}
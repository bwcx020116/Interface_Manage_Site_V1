package iie.ac.cn.site.dao;

import iie.ac.cn.site.model.Reverseapi;
import iie.ac.cn.site.model.ReverseapiExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReverseapiMapper {
    long countByExample(ReverseapiExample example);

    int deleteByExample(ReverseapiExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Reverseapi record);

    int insertSelective(Reverseapi record);

    List<Reverseapi> selectByExample(ReverseapiExample example);

    Reverseapi selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Reverseapi record, @Param("example") ReverseapiExample example);

    int updateByExample(@Param("record") Reverseapi record, @Param("example") ReverseapiExample example);

    int updateByPrimaryKeySelective(Reverseapi record);

    int updateByPrimaryKey(Reverseapi record);
}
package iie.ac.cn.site.service;


import com.alibaba.fastjson.JSONObject;
import iie.ac.cn.site.model.Api;
import iie.ac.cn.site.model.Reverseapi;

import java.util.List;
import java.util.Map;

public interface IReverApiService {
    /**
     * 查询所有
     *
     * @return
     */
    List<Reverseapi> query();

    /**
     * 总数
     *
     * @return
     */
    long total();

    /**
     * 查询数量
     *模糊查询
     * @param nameLike
     * @return
     */
    long countByName(String nameLike);

    /**
     * 根据名称模糊查询
     * @param likelyName
     * @return
     */
    long countByLikelyName(String likelyName);

    /**
     * 分页查询
     *
     * @param rowIndex 开始记录索引
     * @param pageSize 查询数量
     * @return
     */
    List<Reverseapi> query(long rowIndex, int pageSize);


    /**
     * 插入
     *
     * @param reverseapi
     */
    int insert(Reverseapi reverseapi);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    Reverseapi loadById(int id);

    /**
     * 根据ID更新
     *
     * @param reverseapi
     * @return 更新的记录数
     */
    int update(Reverseapi reverseapi);

    /**
     * 根据ID删除
     *
     * @param id ID
     * @return 删除的记录数
     */
    int delete(int id);
    /**
     * @param name
     * @param rowIndex
     * @param pageSize
     * @param orderByClause 排序
     * @return
     */
    List<Reverseapi> queryByName(String name, long rowIndex, int pageSize, String orderByClause);

    /**
     * 根据名称模糊查询
     * @param name
     * @param rowIndex
     * @param pageSize
     * @param orderByClause
     * @return
     */
    List<Reverseapi> queryByLikelyName(String name, long rowIndex, int pageSize, String orderByClause);


    /**
     * @param name
     * @return
     */
    Reverseapi loadByName(String name);
    /**
     * @param name
     * @return
     */
    List<Reverseapi> query(String name);

    
    List<Map<String, Object>> SearchNotPage(String index, String type, JSONObject jsonObj, List<String> outPutTemplate);

    /**
     * 根据输入模板和输出模板查询
     * @param inputStrs 输入模板  字段
     * @param metadataNames 输出模板  元数据
     * @param jsonObj   用户输入的查询值
     * @return
     */
    List<String>  reverseSearch(List<String> inputStrs, List<String> metadataNames,
                                            JSONObject jsonObj,String searchType);

    /**
     * 根据起始时间和结束时间过滤事件
     * @param start_time
     * @param end_time
     * @return
     */
    List<String> filterIncidentByTimeFrame(String index,String type,String start_time, String end_time);
}

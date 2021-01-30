package iie.ac.cn.site.service;


import com.alibaba.fastjson.JSONObject;
import iie.ac.cn.site.model.Api;

import java.util.List;
import java.util.Map;

public interface IApiService {
    /**
     * 查询所有
     *
     * @return
     */
    List<Api> query();

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
    List<Api> query(long rowIndex, int pageSize);


    /**
     * 插入
     *
     * @param api
     */
    int insert(Api api);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    Api loadById(int id);

    /**
     * 根据ID更新
     *
     * @param api
     * @return 更新的记录数
     */
    int update(Api api);

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
    List<Api> queryByName(String name, long rowIndex, int pageSize, String orderByClause);

    /**
     * 根据名称模糊查询
     * @param name
     * @param rowIndex
     * @param pageSize
     * @param orderByClause
     * @return
     */
    List<Api> queryByLikelyName(String name, long rowIndex, int pageSize, String orderByClause);


    /**
     * @param name
     * @return
     */
    Api loadByName(String name);
    /**
     * @param name
     * @return
     */
    List<Api> query(String name);

    
    List<Map<String, Object>> SearchNotPage(String index, String type, JSONObject jsonObj, List<String> outPutTemplate);
}

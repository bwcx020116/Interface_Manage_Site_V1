package iie.ac.cn.site.service;


import iie.ac.cn.site.model.Template;

import java.util.List;

public interface ITemplateService {
    /**
     * 查询所有
     *
     * @return
     */
    List<Template> query();

    /**
     * 总数
     *
     * @return
     */
    long total();

    /**
     * 查询数量
     *
     * @param nameLike
     * @return
     */
    long countByName(String nameLike);

    /**
     * 分页查询
     *
     * @param rowIndex 开始记录索引
     * @param pageSize 查询数量
     * @return
     */
    List<Template> query(long rowIndex, int pageSize);


    /**
     * 分页查询
     *
     * @param nameLike 标题
     * @param rowIndex 开始记录索引
     * @param pageSize 查询数量
     * @return
     */
    List<Template> queryByName(String nameLike, long rowIndex, int pageSize);


    /**
     * 插入
     *
     * @param stopword
     */
    int insert(Template stopword);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    Template loadById(int id);

    /**
     * 根据ID更新
     *
     * @param stopword
     * @return 更新的记录数
     */
    int update(Template stopword);

    /**
     * 根据ID删除
     *
     * @param id ID
     * @return 删除的记录数
     */
    int delete(int id);


    /**
     * @param nameLike
     * @param rowIndex
     * @param pageSize
     * @param orderByClause 排序
     * @return
     */
    List<Template> queryByName(String nameLike, long rowIndex, int pageSize, String orderByClause);



    Template loadByNameAndSrc(String name,String src);

    /**
     * 查询不同的元数据类型
     * @return
     */
    List<Template> queryDistinctSrc();

    /**
     * @param src
     * @return
     */
    List<Template> queryBySrc(String src);

    /**
     * 根据name获取template
     * @param name
     * @return
     */
    List<Template> queryByName(String name);


    List<Template> queryByNameAndSrc(String name,String src);
    List<Template> queryByTypeAndSrc(String type,String src);

    /**
     * 查询所有的不同属性的模板
     * @return
     */
    List<Template> queryAllDistinctName();

    /**
     * 根据字段名称查询模板
     * @param o
     * @return
     */
    List<Template> queryByAtt(String o);
}

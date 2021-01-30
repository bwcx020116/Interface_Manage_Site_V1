package iie.ac.cn.site.service;

import iie.ac.cn.site.model.User;

import java.util.List;

public interface IUserService {
    /**
     * 查询所有
     *
     * @return
     */
    List<User> query();

    /**
     * 总数
     *
     * @return
     */
    long total();

    /**
     * 查询数量
     *
     * @param loginNameLike
     * @return
     */
    long countByLoginName(String loginNameLike);

    /**
     * 分页查询
     *
     * @param loginNameLike 名称
     * @param rowIndex 开始记录索引
     * @param pageSize 查询数量
     * @return
     */
    List<User> queryByLoginName(String loginNameLike, long rowIndex, int pageSize);


    /**
     * 插入
     *
     * @param user
     */
    int insert(User user);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    User loadById(int id);

    /**
     * 根据ID更新
     *
     * @param user
     * @return 更新的记录数
     */
    int update(User user);

    /**
     * 根据ID删除
     *
     * @param id ID
     * @return 删除的记录数
     */
    int delete(int id);


    /**
     *
     * @param loginNameLike
     * @param rowIndex
     * @param pageSize
     * @param orderByClause 排序信息
     * @return
     */
    List<User> queryByLoginName(String loginNameLike, long rowIndex, int pageSize, String orderByClause);
    /**
     *
     * @param loginName
     * @param loginPwd
     * @return
     */
    User query(String loginName,String loginPwd);
}

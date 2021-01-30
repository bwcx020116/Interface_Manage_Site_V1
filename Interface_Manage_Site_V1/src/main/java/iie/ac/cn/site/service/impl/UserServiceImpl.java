package iie.ac.cn.site.service.impl;

import iie.ac.cn.site.dao.UserMapper;
import iie.ac.cn.site.model.User;
import iie.ac.cn.site.model.UserExample;
import iie.ac.cn.site.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements IUserService {
    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;



    @Override
    public List<User> query() {
        return null;
    }

    @Override
    public long total() {
        return 0;
    }

    @Override
    public long countByLoginName(String loginNameLike) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginNameEqualTo(loginNameLike);
        List<User> users = userMapper.selectByExample(userExample);
        if(users!=null)
        {
            return  users.size();
        }else{
            return 0;
        }
    }

    @Override
    public List<User> queryByLoginName(String loginNameLike, long rowIndex, int pageSize) {
        return null;
    }

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public User loadById(int id) {
        return null;
    }

    /**
     * 根据主键更新，先查询主键
     * @param user
     * @return
     */
    @Override
    public int update(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginNameEqualTo(user.getLoginName());
        return userMapper.updateByExample(user,userExample);
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public List<User> queryByLoginName(String loginNameLike, long rowIndex, int pageSize, String orderByClause) {
        return null;
    }

    /**
     * 按照用户名和密码查询
     * @param loginName
     * @param loginPwd
     * @return
     */
    @Override
    public User query(String loginName, String loginPwd) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginNameEqualTo(loginName).andLoginPwdEqualTo(loginPwd);
        List<User> users = userMapper.selectByExample(userExample);
        return users.isEmpty()?null:users.get(0);
    }
}

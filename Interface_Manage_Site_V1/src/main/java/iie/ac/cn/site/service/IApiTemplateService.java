package iie.ac.cn.site.service;


import iie.ac.cn.site.model.ApiTemplate;
import iie.ac.cn.site.model.Template;

import java.util.List;

public interface IApiTemplateService {
    /**
     * 根据API id查询所有的template id
     * @return
     */
    List<Template> queryByApiID(int id);

    /**
     * 根据API id删除对应的关系
     * @return
     */
    int delByApiID(int id);

    /**
     * 根据API ID和template ID插入表中
     * @param ApiID
     * @param templateID
     * @return
     */
    int insertByApiIDAndTempID(int ApiID,int templateID);

}

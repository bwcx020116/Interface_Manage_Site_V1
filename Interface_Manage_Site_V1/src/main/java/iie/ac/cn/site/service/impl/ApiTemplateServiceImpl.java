package iie.ac.cn.site.service.impl;

import iie.ac.cn.site.dao.ApiTemplateMapper;
import iie.ac.cn.site.dao.TemplateMapper;
import iie.ac.cn.site.model.ApiTemplate;
import iie.ac.cn.site.model.ApiTemplateExample;
import iie.ac.cn.site.model.Template;
import iie.ac.cn.site.service.IApiTemplateService;
import iie.ac.cn.site.service.ITemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ApiTemplateServiceImpl implements IApiTemplateService {

    @Autowired
    private ApiTemplateMapper ApiTemplateMapper;

    @Autowired
    private ITemplateService templateService;

    public List<Template> queryByApiID(int id) {
        ApiTemplateExample example = new ApiTemplateExample();
        example.createCriteria().andApiIdEqualTo(id);
        List<ApiTemplate> apiTemplates = this.ApiTemplateMapper.selectByExample(example);
        List<Template> templates = new ArrayList<>();
        if(apiTemplates!=null){
            for(ApiTemplate apiTemplate: apiTemplates){
                templates.add(this.templateService.loadById(apiTemplate.getTemplateId()));
            }
            return templates;
        }
        return null;
    }

    /**
     * 根据API ID删除
     * @param id
     */
    @Override
    public int delByApiID(int id) {
        ApiTemplateExample example = new ApiTemplateExample();
        example.createCriteria().andApiIdEqualTo(id);
        return this.ApiTemplateMapper.deleteByExample(example);
    }

    @Override
    public int insertByApiIDAndTempID(int ApiID, int templateID) {
        ApiTemplate apiTemplate = new ApiTemplate();
        apiTemplate.setApiId(ApiID);
        apiTemplate.setTemplateId(templateID);
        return this.ApiTemplateMapper.insert(apiTemplate);
    }

}
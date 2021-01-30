package iie.ac.cn.site.controller;

import com.alibaba.fastjson.JSONObject;
import iie.ac.cn.site.common.BaseResult;
import iie.ac.cn.site.model.Api;
import iie.ac.cn.site.model.Template;
import iie.ac.cn.site.service.IApiService;
import iie.ac.cn.site.service.IApiTemplateService;
import iie.ac.cn.site.service.ITemplateService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/forwardconfigure")
public class ForwardConfigureController {
    private static final Log logger = LogFactory
            .getLog(ForwardConfigureController.class);
    @Autowired
    private IApiService iApiService;

    @Autowired
    private ITemplateService templateService;

    @Autowired
    private IApiTemplateService iApiTemplateService;

    /**
     * 首页
     * @return
     */
    @RequestMapping(value = {"","index.html"})
    public String index(HttpServletRequest request,
                        HttpServletResponse response, Model model) {
        List<Template> templates = this.templateService.queryDistinctSrc();
        model.addAttribute("templateList",templates);
        return "forwardconfigure/index";
    }


    @RequestMapping({"{metadata}/getMetadataTemplate.json"})
    @ResponseBody
    public List<Template> getMetadataTemplate(@PathVariable  String metadata) {
        List<Template> templateList=new ArrayList<>();
        if(metadata!=null){
            templateList=this.templateService.queryBySrc(metadata);
        }
        return  templateList;
    }

    /**
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("save.json")
    @ResponseBody
    public BaseResult save(HttpServletRequest request,
                       HttpServletResponse response, @RequestBody JSONObject object) {
        BaseResult baseResult = new BaseResult();
        String metadata =(String) object.get("metadata");
        if((" ").equals(metadata)||("").equals(metadata)){
            baseResult.setSuccess(false);
            baseResult.setResultMsg("metadata name is not empty");
            return baseResult;
        }

        String name =(String) object.get("name");
        if((" ").equals(name)||("").equals(name)||name==null){
            baseResult.setSuccess(false);
            baseResult.setResultMsg("API name is not empty");
            return baseResult;
        }
        //查询模板id
        List<Integer> templateIDs=new ArrayList<>();
        Set<String> keySet = object.keySet();
        for(String k:keySet){
            if("name".equals(k)||"description".equals(k)||"metadata".equals(k)){
                continue;
            }
            String o = (String) object.get(k);
            if((" ").equals(o)||("").equals(o)||o==null){
                baseResult.setSuccess(false);
                baseResult.setResultMsg("template is empty");
                return baseResult;
            }

            List<Template> templates = this.templateService.queryByNameAndSrc(o, metadata);
            if(templates!=null){
                Template template = templates.get(0);
                templateIDs.add(template.getId());
            }else{
                baseResult.setSuccess(false);
                baseResult.setResultMsg("please upload template");
                return baseResult;
            }
        }
        Api api = this.iApiService.loadByName(name);//API名称重复
        if(api!=null){
            baseResult.setSuccess(false);
            baseResult.setResultMsg("The name of API is duplicated");
            return baseResult;
        }
        String description =(String) object.get("description");

        this.iApiService.insert(new Api(name,description,metadata,"正向查询"));
        List<Api> apis= this.iApiService.query(name);
        Integer apiID=null;
        if(apis!=null){
            Api api1 = apis.get(0);
            apiID= api1.getId();
        }else{
            baseResult.setSuccess(false);
            baseResult.setResultMsg("configure failed");
            return baseResult;
        }
       for(Integer ids:templateIDs){
          this.iApiTemplateService.insertByApiIDAndTempID(apiID, ids);
       }
       baseResult.setSuccess(true);
       baseResult.setResultMsg("configure successfully");
       return baseResult;
    }
}

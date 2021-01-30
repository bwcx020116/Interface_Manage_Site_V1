package iie.ac.cn.site.controller;

import com.alibaba.fastjson.JSONObject;
import iie.ac.cn.site.common.BaseResult;
import iie.ac.cn.site.model.Api;
import iie.ac.cn.site.model.Metadata;
import iie.ac.cn.site.model.Reverseapi;
import iie.ac.cn.site.model.Template;
import iie.ac.cn.site.service.*;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/reversedconfigure")
public class ReversedConfigureController {
    private static final Log logger = LogFactory
            .getLog(ReversedConfigureController.class);
    @Autowired
    private IApiService iApiService;

    @Autowired
    private ITemplateService templateService;

    @Autowired
    private IApiTemplateService iApiTemplateService;

    @Autowired
    private IMetadataService iMetadataService;

    @Autowired
    private IReverApiService iReverApiService;
    @Autowired
    private IReverseApiMetaService iReverseApiMetaService;
    /**
     * 首页
     * @return
     */
    @RequestMapping(value = {"","index.html"})
    public String index(HttpServletRequest request,
                        HttpServletResponse response, Model model) {
        List<Template> templates = this.templateService.queryAllDistinctName();
        List<Metadata> metadatas = this.iMetadataService.queryAll();
        model.addAttribute("templateList",templates);
        model.addAttribute("metadataList",metadatas);
        return "reversedconfigure/index";
    }

    @RequestMapping({"getAllAttName.json"})
    @ResponseBody
    public List<Template> getAllAttName() {
        List<Template> templates = this.templateService.queryAllDistinctName();
        if(templates!=null){
            return templates;
        }else{
            return null;
        }
    }
    @RequestMapping({"getAllMetadata.json"})
    @ResponseBody
    public List<Metadata> getAllMetadata() {
        List<Metadata> metadataList = this.iMetadataService.queryAll();//ip/sample/url
        if(metadataList!=null){
            return metadataList;
        }else{
            return null;
        }
    }


    @RequestMapping({"{attname}/getMetadataByAttname.json"})
    @ResponseBody
    public List<Template> getMetadataByAttname(@PathVariable  String attname) {
        List<Template> templateList=new ArrayList<>();
        if(attname!=null){
            templateList=this.templateService.queryByName(attname);
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
        String interfaceName =(String) object.get("name");
        if((" ").equals(interfaceName)||("").equals(interfaceName)){
            baseResult.setSuccess(false);
            baseResult.setResultMsg("Interface name can not be empty!");
            return baseResult;
        }

        Reverseapi api = this.iReverApiService.loadByName(interfaceName);//API名称重复
        if(api!=null){
            baseResult.setSuccess(false);
            baseResult.setResultMsg("The name of API is duplicated");
            return baseResult;
        }

        String description =(String) object.get("description");
        if((" ").equals(description)||("").equals(description)||description==null){
            baseResult.setSuccess(false);
            baseResult.setResultMsg("Please make some description  to API");
            return baseResult;
        }
        String searchtype =(String) object.get("searchtype");
        if((" ").equals(description)||("").equals(description)||description==null){
            baseResult.setSuccess(false);
            baseResult.setResultMsg("Please select search type");
            return baseResult;
        }
        //查询模板id
        List<Integer> templateIDs=new ArrayList<>();
        List<Integer> metadataIDs=new ArrayList<>();
        Set<String> keySet = object.keySet();
        for(String k:keySet){
            if("name".equals(k)||"description".equals(k)||"metadata".equals(k)||"searchtype".equals(k)){
                continue;
            }
            String o = (String) object.get(k);
            if((" ").equals(o)||("").equals(o)||o==null){
                baseResult.setSuccess(false);
                baseResult.setResultMsg("请选择字段类型或元数据类型!");
                return baseResult;
            }

            if(k.startsWith("attname_")){
                List<Template> templates = this.templateService.queryByAtt(o);
                if(templates!=null){
                    Template template = templates.get(0);
                    templateIDs.add(template.getId());
                }else{
                    baseResult.setSuccess(false);
                    baseResult.setResultMsg("please upload template");
                    return baseResult;
                }
            }else if(k.startsWith("name_")){
                List<Metadata> metadataList = this.iMetadataService.queryByName(o);
                if(metadataList!=null){
                    Metadata metadata = metadataList.get(0);
                    metadataIDs.add(metadata.getId());
                }else{
                    baseResult.setSuccess(false);
                    return baseResult;
                }
            }
        }

        Reverseapi reverseapi=null;
        if(searchtype.equals("精确查询")){//精确匹配输出库中插入1，模糊查询插入2
            reverseapi=new Reverseapi(interfaceName,description,
                    templateIDs.toString().replace("[","").replace("]",""),"反向查询","1");
        }else if(searchtype.equals("模糊查询")){
            reverseapi=new Reverseapi(interfaceName,description,
                    templateIDs.toString().replace("[","").replace("]",""),"反向查询","2");
        }
        this.iReverApiService.insert(reverseapi);

        List<Reverseapi> reverseapis= this.iReverApiService.query(interfaceName);
        Integer apiID=null;
        if(reverseapis!=null){
            Reverseapi api1 = reverseapis.get(0);
            apiID= api1.getId();
        }else{
            baseResult.setSuccess(false);
            baseResult.setResultMsg("configure failed");
            return baseResult;
        }
       for(Integer ids:metadataIDs){
          this.iReverseApiMetaService.insertByApiIDAndMetaID(apiID, ids);
       }
       baseResult.setSuccess(true);
       baseResult.setResultMsg("configure successfully");
       return baseResult;
    }
}

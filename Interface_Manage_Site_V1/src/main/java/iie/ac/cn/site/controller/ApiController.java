package iie.ac.cn.site.controller;

import com.alibaba.fastjson.JSONObject;
import iie.ac.cn.site.common.BaseResult;
import iie.ac.cn.site.common.BootstrapTable;
import iie.ac.cn.site.common.QueryParam;
import iie.ac.cn.site.model.Api;
import iie.ac.cn.site.model.Metadata;
import iie.ac.cn.site.model.Template;
import iie.ac.cn.site.service.*;
import iie.ac.cn.site.util.PropertyUtil;
import iie.ac.cn.site.vo.ApiVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Controller
@RequestMapping("/api")
public class ApiController {

    private String common_prefix="";
    private String index="cti_incident_1";
    private String type="doc_type";
    @Autowired
    private ITemplateService iTemplateService;
    @Autowired
    private IApiTemplateService iApiTemplateService;
    @Autowired
    private IApiService iApiService;
    @Autowired
    private IMetadataService iMetadataService;




    @RequestMapping(value = {"","index.html"})
    public String index(HttpServletRequest request,
                        HttpServletResponse response) {
        return "api/index";
    }
    @RequestMapping({"getAllAttName.json"})
    @ResponseBody
    public List<Template> getAllAttName(@RequestBody JSONObject jsonObject) {
        List<Template> templates = this.iTemplateService.queryBySrc((String)jsonObject.get("metadata"));
        if(templates!=null){
            return templates;
        }else{
            return null;
        }
    }
    /**
     * 分页展示API名称信息
     * @param queryParam 查询条件
     * @return
     */
    @RequestMapping("query_by_page.json")
    @ResponseBody
    public BootstrapTable queryByPage(@RequestBody(required = false) final QueryParam queryParam
    ) {
        String search = String.valueOf(queryParam.getSearch().get("searchLike"));
        long total = this.iApiService.countByLikelyName(search);
        long pageIndex = (queryParam.getPageNumber() - 1) * queryParam.getPageSize();
        String orderByClause = PropertyUtil.toUnderline(queryParam.getSortName()) + " " + queryParam.getSortOrder();
        //添加前缀
        List<Api> apis = this.iApiService.queryByLikelyName(search, pageIndex, queryParam.getPageSize(), orderByClause);
        apis=addApiWithPrefix(apis);
        List<ApiVo> apiVos=new ArrayList<>();
        if(apis!=null){
            for(Api a:apis){
                //获得输入参数
                List<String> metadataStrs=new ArrayList<>();
                metadataStrs.add(a.getMetadata());
                //获得输出参数
                List<Template> outputTemplate = this.iApiTemplateService.queryByApiID(a.getId());
                if(outputTemplate==null){
                    continue;
                }
                List<String> outputTemplateStr = getMetadataStr(outputTemplate);
                apiVos.add(new ApiVo(a.getId(),a.getName(),a.getDescription(),a.getMetadata(),a.getType(),metadataStrs,outputTemplateStr));
            }
        }
        return new BootstrapTable(total, apiVos);
    }

    /**
     * 编辑页面
     * @param model
     * @param id    ID
     * @return
     */
    @RequestMapping({"{id}/edit.html"})
    public String toEdit(Model model, @PathVariable final int id) {
        //根据ID查询元数据信息
        Api api = this.iApiService.loadById(id);
        List<String> inputParams=new ArrayList<>();
        inputParams.add(api.getMetadata());
        List<Template> templates = this.iApiTemplateService.queryByApiID(api.getId());
        List<String> outputParams = getMetadataStr(templates);
        ApiVo apiVo = new ApiVo(api.getId(), api.getName(), api.getDescription(), api.getMetadata(), api.getType(),
                inputParams, outputParams);
        model.addAttribute("apivo", apiVo);

        List<Template> templateAll = this.iTemplateService.queryBySrc(api.getMetadata());
        List<String> templateList=getAttStr(templateAll);
        model.addAttribute("templateList",templateList);

        List<Metadata> metadatas = iMetadataService.queryAll();
        List<String> metadataList=new ArrayList<>();
        for(Metadata t:metadatas){
            metadataList.add(t.getName());
        }
        model.addAttribute("metadataList",metadataList);
        model.addAttribute("att_counter",apiVo.getOutputParam().size());
        return "api/new";
    }
    /**
     * 根据属性名称
     * @param templateList
     * @return
     */
    private List<String> getAttStr(List<Template> templateList) {
        List<String> res=new ArrayList<>();
        if(templateList!=null){
            for(Template t:templateList){
                res.add(t.getName()+"\n");
            }
            return res;
        }
        return null;
    }
    /**
     * 新增或更新
     * @param request
     * @param response
     * @param jsonObj
     * @return
     */
    @RequestMapping("save.json")
    @ResponseBody
    public BaseResult save(HttpServletRequest request,
                           HttpServletResponse response, @RequestBody JSONObject jsonObj) {
        int count = 0;
        BaseResult result = new BaseResult();
        if(jsonObj.isEmpty()){
            result.setSuccess(false);
            return result;
        }
        Set<String> keys = jsonObj.keySet();

        String id_str= (String) jsonObj.get("id");
        Integer id=Integer.parseInt(id_str);
        String metadata= (String) jsonObj.get("metadata");
        String description= (String) jsonObj.get("description");
        String name= (String) jsonObj.get("name");
        List<Integer> templateIDs=new ArrayList<>();
        for(String key:keys){
            String value=(String)jsonObj.get(key);
            value=value.trim().replace("\r\n","");
            if(key.startsWith("attname_")){
                List<Template> templates = this.iTemplateService.queryByNameAndSrc(value,metadata);
                if(templates!=null) {
                    Template template = templates.get(0);
                    templateIDs.add(template.getId());
                }
            }
        }
        Api api = new Api(id, name, description, metadata, "正向查询");
        if (api.getId() == null || api.getId() == 0) {
            //将api添加到DB中
            count = this.iApiService.insert(api);
            if(count<0){
                result.setSuccess(false);
                return result;
            }
        } else {
            //根据ID更新api
            count = this.iApiService.update(api);
            if(count<0){
                result.setSuccess(false);
                return result;
            }else{
                //更新关联表：先删除后插入，该方法有待更新
                int res = iApiTemplateService.delByApiID(api.getId());
                if(res<0){
                    result.setSuccess(false);
                    return result;
                }
                for(Integer meta_id:templateIDs){
                    iApiTemplateService.insertByApiIDAndTempID(api.getId(),meta_id);
                }
            }
        }

        result.setSuccess(true);
        return result;
    }

    /**
     * 根据元数据类型获取各个字段名称
     * @param templates
     * @return
     */
    private List<String> getMetadataStr(List<Template> templates) {
        List<String> res=new ArrayList<>();
        if(templates!=null){
            for(Template t:templates){
                res.add(t.getName()+"\n");
            }
            return res;
        }
        return null;
    }

    /**
     * 将api名称添加前缀http://localhost:9001/api/...
     * @param apis
     * @return
     */
    private List<Api> addApiWithPrefix(List<Api> apis) {
        if(apis!=null){
            for(Api api:apis){
                if("incident".equals(api.getMetadata())){
                    api.setName(common_prefix+"queryIncidentData/"+api.getName());
                }
            }
            return apis;
        }
        return null;
    }
    /**
     * 根据ID删除API名称及描述
     * @param id
     * @return
     */
    @RequestMapping("delete.json")
    @ResponseBody
    public BaseResult delete(@RequestParam final int id) {
        //根据ID删除元数据
        int count = this.iApiService.delete(id);
        int delRes = this.iApiTemplateService.delByApiID(id);
        BaseResult result = new BaseResult();
        //如果删除成功返回客户端true,否则返回false
        result.setSuccess((count > 0)&&(delRes>0) ? true : false);
        return result;
    }

    @RequestMapping("queryIncidentData/{configName}")
    @ResponseBody
    public  List<Map<String,Object>> queryByIncidentApiName(@PathVariable final String configName,@RequestBody JSONObject jsonObj) {
        if(configName.equals(" ")||configName.equals("")||configName==null){
            return null;
        }
        Api api = this.iApiService.loadByName(configName);
        if(api==null){
            return null;
        }else{
            List<Map<String,Object>> searchResults=new ArrayList<>();
            List<Template> templates = this.iApiTemplateService.queryByApiID(api.getId());
            if (templates == null) {
                return null;
            }else{
                List<String> templateName=new ArrayList<>();
                for(Template t:templates){
                    templateName.add(t.getName());
                }
                searchResults= iApiService.SearchNotPage(index, type,jsonObj,templateName);
            }
            if (searchResults.size()<1){
                return null;
            }
            return searchResults;
        }
    }

}

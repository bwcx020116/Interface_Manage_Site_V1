package iie.ac.cn.site.controller;

import com.alibaba.fastjson.JSONObject;
import iie.ac.cn.site.common.BaseResult;
import iie.ac.cn.site.common.BootstrapTable;
import iie.ac.cn.site.common.QueryParam;
import iie.ac.cn.site.model.Metadata;
import iie.ac.cn.site.model.Reverseapi;
import iie.ac.cn.site.model.Template;
import iie.ac.cn.site.service.IMetadataService;
import iie.ac.cn.site.service.IReverApiService;
import iie.ac.cn.site.service.IReverseApiMetaService;
import iie.ac.cn.site.service.ITemplateService;
import iie.ac.cn.site.util.PropertyUtil;
import iie.ac.cn.site.vo.ReverseapiVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Controller
@RequestMapping("/reverse_api")
public class ReverseApiController {

    private String common_prefix="";
    @Autowired
    private IReverseApiMetaService iReverseApiMetaService;
    @Autowired
    private ITemplateService iTemplateService;
    @Autowired
    private IMetadataService iMetadataService;

    @Autowired
    private IReverApiService iReverseapiService;


    @RequestMapping(value = {"","index.html"})
    public String index(HttpServletRequest request,
                        HttpServletResponse response) {
        return "reverse_api/index";
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
        long total = this.iReverseapiService.countByLikelyName(search);
        long pageIndex = (queryParam.getPageNumber() - 1) * queryParam.getPageSize();
        String orderByClause = PropertyUtil.toUnderline(queryParam.getSortName()) + " " + queryParam.getSortOrder();
        //添加前缀
        List<Reverseapi> reverseapis = this.iReverseapiService.queryByLikelyName(search, pageIndex, queryParam.getPageSize(), orderByClause);
        reverseapis=addReverseapiWithPrefix(reverseapis);
        List<ReverseapiVo> reverseapiVos=new ArrayList<>();
        if(reverseapis!=null){
            for(Reverseapi a:reverseapis){
                //获得输入参数
                //将属性ID List转化为属性名称[23,34]->['ip_version','short_description']
                List<String> inputStrs=changeAttID2Name(a.getAttribution());
                //获得输出参数
                List<Metadata> metedataList = this.iReverseApiMetaService.queryByApiID(a.getId());
                List<String> outputStr = getMetadataStr(metedataList);
                ReverseapiVo reverseapiVo=null;
                if("1".equals(a.getSearchtype())){
                    reverseapiVo=new ReverseapiVo(a.getId(),a.getName(),a.getDescription(),a.getAttribution(),a.getApitype(),"精确查询",
                            inputStrs,outputStr);
                }else if("2".equals(a.getSearchtype())){
                    reverseapiVo=new ReverseapiVo(a.getId(),a.getName(),a.getDescription(),a.getAttribution(),a.getApitype(),"模糊查询",
                            inputStrs,outputStr);
                }
                reverseapiVos.add(reverseapiVo);
            }
        }
        return new BootstrapTable(total, reverseapiVos);
    }

    /**
     *
     * @param attribution：'23,34,45'
     * @return
     */
    private List<String> changeAttID2Name(String attribution) {
        String[] strs = attribution.split(", ");
        if(strs.length<1){
            return null;
        }else{
            List<String> res=new ArrayList<>();
            for(int i=0;i<strs.length;i++){
                res.add(iTemplateService.loadById(Integer.parseInt(strs[i].trim())).getName());
            }
            return res;
        }
    }

    /**
     * 根据元数据类型获取各个字段名称
     * @param metadataList
     * @return
     */
    private List<String> getMetadataStr(List<Metadata> metadataList) {
        List<String> res=new ArrayList<>();
        if(metadataList!=null){
            for(Metadata t:metadataList){
                res.add(t.getName()+"\n");
            }
            return res;
        }
        return null;
    }

    /**
     * 将reverseapi名称添加前缀http://localhost:9001/reverseapi/...
     * @param reverseapis
     * @return
     */
    private List<Reverseapi> addReverseapiWithPrefix(List<Reverseapi> reverseapis) {
        if(reverseapis!=null){
            for(Reverseapi reverseapi:reverseapis){
                reverseapi.setName(common_prefix+reverseapi.getName());
            }
            return reverseapis;
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
        int count = this.iReverseapiService.delete(id);
        int delRes = this.iReverseApiMetaService.delByApiID(id);
        BaseResult result = new BaseResult();
        //如果删除成功返回客户端true,否则返回false
        result.setSuccess((count > 0)&&(delRes>0) ? true : false);
        return result;
    }

    @RequestMapping({"getAllAttName.json"})
    @ResponseBody
    public List<Template> getAllAttName() {
        List<Template> templates = this.iTemplateService.queryAllDistinctName();
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


    /**
     * 编辑页面
     * @param model
     * @param id    ID
     * @return
     */
    @RequestMapping({"{id}/edit.html"})
    public String toEdit(Model model, @PathVariable final int id) {
        //根据ID查询元数据信息
        Reverseapi a = this.iReverseapiService.loadById(id);
        List<String> inputStrs=changeAttID2Name(a.getAttribution());
        List<Metadata> metedataList = this.iReverseApiMetaService.queryByApiID(a.getId());
        List<String> outputStr = getMetadataStr(metedataList);
        ReverseapiVo reverseapiVo=null;
        if("1".equals(a.getSearchtype())){
            reverseapiVo=new ReverseapiVo(a.getId(),a.getName(),a.getDescription(),a.getAttribution(),a.getApitype(),"精确查询",
                    inputStrs,outputStr);
        }else if("2".equals(a.getSearchtype())){
            reverseapiVo=new ReverseapiVo(a.getId(),a.getName(),a.getDescription(),a.getAttribution(),a.getApitype(),"模糊查询",
                    inputStrs,outputStr);
        }
        model.addAttribute("reverseapivo",reverseapiVo);
        //templates
        List<Template> templates=new ArrayList<>();
        String[] strs = a.getAttribution().split(", ");
        if(strs!=null){
            for(int i=0;i<strs.length;i++){
                templates.add(iTemplateService.loadById(Integer.parseInt(strs[i].trim())));
            }
        }
        List<Template> templateList = iTemplateService.queryAllDistinctName();

        if (templateList!=null){
            model.addAttribute("templateList",templateList);
            model.addAttribute("att_counter",reverseapiVo.getInputParam().size());
        }
        List<String> allMetaNames=getMetadataStr(iMetadataService.queryAll());
        if(allMetaNames!=null) {
            model.addAttribute("origindataList", allMetaNames);//所有的元数据
            model.addAttribute("meta_counter",reverseapiVo.getOutputParam().size());
        }
        return "reverse_api/new";
    }


    /**
     * 新增或更新
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("save.json")
    @ResponseBody
    public BaseResult save(HttpServletRequest request,
                           HttpServletResponse response,  @RequestBody JSONObject jsonObj) {
        int count = 0;
        if(jsonObj.isEmpty()){
            return null;
        }
        Set<String> keys = jsonObj.keySet();
        List<Integer> metadataIDs=new ArrayList<>();
        List<Integer> templateIDs=new ArrayList<>();
        for(String key:keys){
            String value=(String)jsonObj.get(key);
            value=value.trim().replace("\r\n","");
            if(key.startsWith("name_")){
                List<Metadata> metadatas = this.iMetadataService.queryByName(value);
                if(metadatas!=null){
                    Metadata metadata = metadatas.get(0);
                    metadataIDs.add(metadata.getId());
                }
            }else if(key.startsWith("attname_")){
                List<Template> templates = this.iTemplateService.queryByAtt(value);
                if(templates!=null) {
                    Template template = templates.get(0);
                    templateIDs.add(template.getId());
                }
            }
        }
        String id_str= (String) jsonObj.get("id");
        Integer id=Integer.parseInt(id_str);
        String apitype= (String) jsonObj.get("apitype");
        String description= (String) jsonObj.get("description");
        String name= (String) jsonObj.get("name");
        String searchtype= (String) jsonObj.get("searchtype");
        Reverseapi reverseapi=null;
        if(searchtype.equals("精确查询")){//精确匹配输出库中插入1，模糊查询插入2
            reverseapi=new Reverseapi(id,name,description,
                    templateIDs.toString().replace("[","").replace("]",""),apitype,"1");
        }else if(searchtype.equals("模糊查询")){
            reverseapi=new Reverseapi(id,name,description,
                    templateIDs.toString().replace("[","").replace("]",""),apitype,"2");
        }
        //如果templateID为0或null,需要增加,否则根据ID更新
        BaseResult result = new BaseResult();
        if (reverseapi.getId() == null || reverseapi.getId() == 0) {
            //将reverse_api添加到数据库中
            count = this.iReverseapiService.insert(reverseapi);
            if(count<0){
                result.setSuccess(false);
                return result;
            }
            for(Integer meta_id:metadataIDs){
                iReverseApiMetaService.insertByApiIDAndMetaID(reverseapi.getId(),meta_id);
            }
        } else {
            //根据ID更新reverse_api
            count = this.iReverseapiService.update(reverseapi);
            if(count<0){
                result.setSuccess(false);
                return result;
            }else{
                 //更新关联表：先删除后插入，该方法有待更新
                int res = iReverseApiMetaService.delByApiID(reverseapi.getId());
                if(res<0){
                    result.setSuccess(false);
                    return result;
                }
                for(Integer meta_id:metadataIDs){
                    iReverseApiMetaService.insertByApiIDAndMetaID(reverseapi.getId(),meta_id);
                }
            }
        }

        result.setSuccess(true);
        return result;
    }

    @RequestMapping("/{configName}")
    @ResponseBody
    public  List<String>  queryBySampleReverseapiName(@PathVariable final String configName,
                                                                 @RequestBody JSONObject jsonObj) {
        if(configName.equals(" ")||configName.equals("")||configName==null){
            return null;
        }
        Reverseapi reverseapi = this.iReverseapiService.loadByName(configName);
        if(reverseapi==null){
            return null;
        }else{
            List<String>  searchResults=new ArrayList<>();
            List<Metadata> metadatas = this.iReverseApiMetaService.queryByApiID(reverseapi.getId());

            if (metadatas == null) {
                return null;
            }else{
                List<String> inputStrs=changeAttID2Name(reverseapi.getAttribution());
                if(inputStrs==null){
                    return null;
                }

                List<String> metadataNames=new ArrayList<>();
                for(Metadata t:metadatas){
                    metadataNames.add(t.getName());
                }
                searchResults= iReverseapiService.reverseSearch(inputStrs,metadataNames,jsonObj,reverseapi.getSearchtype());
            }
            if (searchResults==null){
                return null;
            }
            return searchResults;
       }
    }

}

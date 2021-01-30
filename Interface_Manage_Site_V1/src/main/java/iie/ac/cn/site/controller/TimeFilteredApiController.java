package iie.ac.cn.site.controller;

import com.alibaba.fastjson.JSONObject;
import iie.ac.cn.site.common.BaseResult;
import iie.ac.cn.site.common.BootstrapTable;
import iie.ac.cn.site.common.QueryParam;
import iie.ac.cn.site.model.Api;
import iie.ac.cn.site.model.Metadata;
import iie.ac.cn.site.model.Template;
import iie.ac.cn.site.service.*;
import iie.ac.cn.site.service.impl.IReverApiServiceImpl;
import iie.ac.cn.site.util.PropertyUtil;
import iie.ac.cn.site.vo.ApiVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/time_filtered_api")
public class TimeFilteredApiController {

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
    @Autowired
    private IReverApiService iReverseapiService;




    @RequestMapping(value = {"","index.html"})
    public String index(HttpServletRequest request,
                        HttpServletResponse response) {
        return "time_filtered_api/index";
    }

    /**
     * 分页展示API名称信息
     * @param queryParam 查询条件
     * @return
     */
    @RequestMapping("query_by_page.json")
    @ResponseBody
    public BootstrapTable queryByPage(@RequestBody final QueryParam queryParam
    ) {
        List<String> input=new ArrayList<>();
        input.add("start_time");
        input.add("end_time");
        List<String> output=new ArrayList<>();
        output.add("incident");
        List<ApiVo> apiVos=new ArrayList<>();
        apiVos.add(new ApiVo(1,common_prefix+"time_filtered_api/queryByDateFrame","时间过滤接口","start_time,end_time"
                ,"基于始末时间区间查询",input,output));
        return new BootstrapTable(apiVos.size(), apiVos);
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
                    api.setName(common_prefix+"time_filtered_api/"+api.getName());
                }
            }
            return apis;
        }
        return null;
    }

    /**
     * 输入两个时间点查询其中的所有时间
     * @param jsonObj
     * @return
     */
    @RequestMapping("queryByDateFrame")
    @ResponseBody
    public  List<String> queryByIncidentApiName(@RequestBody JSONObject jsonObj) {
        Set<String> keySet = jsonObj.keySet();
        List<String> results=new ArrayList<>();
        if(keySet.size()!=2){
            results.add("请输出两个时间点!");
            return results;
        }else{
            if(!(keySet.contains("start_time")&&keySet.contains("end_time")))
            {
                results.add("请输入正确的参数名称！");
                return results;
            }else{
                String start_str=(String)jsonObj.get("start_time");
                String end_str=(String)jsonObj.get("end_time");
                String start_time= IReverApiServiceImpl.time2timestamp(start_str);
                String end_time=IReverApiServiceImpl.time2timestamp(end_str);
                results=this.iReverseapiService.filterIncidentByTimeFrame(index,type,start_time,end_time);
            }
        }
        return results;
    }



}

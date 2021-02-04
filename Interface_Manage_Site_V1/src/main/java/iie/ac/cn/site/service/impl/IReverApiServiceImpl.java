package iie.ac.cn.site.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import iie.ac.cn.site.dao.ReverseapiMapper;
import iie.ac.cn.site.model.*;
import iie.ac.cn.site.service.IApiService;
import iie.ac.cn.site.service.IReverApiService;
import iie.ac.cn.site.service.ITemplateService;
import iie.ac.cn.site.util.ESClientUtils;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class IReverApiServiceImpl implements IReverApiService {
    private String index="";
    private String type="";

    @Autowired
    private ReverseapiMapper ReverapiMapper;
    @Autowired
    private ITemplateService iTemplateService;
    @Autowired
    private IApiService iApiService;

    public List<Reverseapi> query() {
        ReverseapiExample example = new ReverseapiExample();
        List<Reverseapi> apiList = this.ReverapiMapper.selectByExample(example);
        return apiList;
    }

    /**
     * 总数
     *
     * @return
     */
    @Override
    public long total() {
        ReverseapiExample example = new ReverseapiExample();
        return this.ReverapiMapper.countByExample(example);
    }

    /**
     * 查询数量
     * @param name
     * @return
     */
    @Override
    public long countByName(String name) {
        ReverseapiExample example = new ReverseapiExample();
        example.createCriteria().andNameEqualTo(name);
        return this.ReverapiMapper.countByExample(example);

    }

    @Override
    public long countByLikelyName(String likelyName) {
        ReverseapiExample example = new ReverseapiExample();
        example.createCriteria().andNameLike("%" + likelyName + "%");
        return this.ReverapiMapper.countByExample(example);
    }

    /**
     * 分页查询
     *
     * @param rowIndex
     * @param pageSize
     * @return
     */
    @Override
    public List<Reverseapi> query(long rowIndex, int pageSize) {

        ReverseapiExample example = new ReverseapiExample();

        example.setRowIndex(rowIndex);
        example.setPageSize(pageSize);

        List<Reverseapi> apiList = this.ReverapiMapper.selectByExample(example);

        return apiList;
    }
    /**
     * @param name
     * @param rowIndex
     * @param pageSize
     * @param orderByClause 排序信息
     * @return
     */
    @Override
    public List<Reverseapi> queryByName(String name, long rowIndex, int pageSize, String orderByClause) {

        ReverseapiExample example = new ReverseapiExample();
        example.setRowIndex(rowIndex);
        example.setPageSize(pageSize);
        example.createCriteria().andNameEqualTo(name);
        if (null != orderByClause) {
            example.setOrderByClause(orderByClause);
        }
        List<Reverseapi> apiList = this.ReverapiMapper.selectByExample(example);

        return apiList;
    }

    @Override
    public List<Reverseapi> queryByLikelyName(String name, long rowIndex, int pageSize, String orderByClause) {
        ReverseapiExample example = new ReverseapiExample();
        example.setRowIndex(rowIndex);
        example.setPageSize(pageSize);
        example.createCriteria().andNameLike("%" + name + "%");
        if (null != orderByClause) {
            example.setOrderByClause(orderByClause);
        }
        List<Reverseapi> apiList = this.ReverapiMapper.selectByExample(example);


        return apiList;
    }


    @Override
    public Reverseapi loadByName(String name) {
        ReverseapiExample example = new ReverseapiExample();
        example.createCriteria().andNameEqualTo(name);
        List<Reverseapi> apiList = this.ReverapiMapper.selectByExample(example);
        return apiList.isEmpty() ? null : apiList.get(0);
    }

    /**
     * 根据名称列出得到API列表
     * @param name
     * @return
     */
    @Override
    public List<Reverseapi> query(String name) {
        ReverseapiExample example = new ReverseapiExample();
        example.createCriteria().andNameEqualTo(name);
        List<Reverseapi> apiList = this.ReverapiMapper.selectByExample(example);
        if(apiList!=null){
            return apiList;
        }else {
            return null;
        }
    }


    /**
     * 插入
     * @param api
     */
    @Override
    public int insert(Reverseapi api) {
        return this.ReverapiMapper.insert(api);

    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Override
    public Reverseapi loadById(int id) {
        ReverseapiExample example = new ReverseapiExample();
        example.createCriteria().andIdEqualTo(id);

        List<Reverseapi> apiList = this.ReverapiMapper.selectByExample(example);

        return apiList.isEmpty() ? null : apiList.get(0);

    }

    /**
     * 根据ID更新
     *
     * @param api
     */
    @Override
    public int update(Reverseapi api) {
        ReverseapiExample example = new ReverseapiExample();
        example.createCriteria().andIdEqualTo(api.getId());
        return this.ReverapiMapper.updateByExample(api, example);
    }

    /**
     * 根据ID删除
     *
     * @param id
     */
    @Override
    public int delete(int id) {
        ReverseapiExample example = new ReverseapiExample();
        example.createCriteria().andIdEqualTo(id);
        return this.ReverapiMapper.deleteByExample(example);
    }

    /**
     * 查询总的记录数 游标滚动
     * @param index
     * @param type
     * @param jsonObj
     * @return
     */
    public  List<Map<String,Object>> SearchNotPage(String index, String type, JSONObject jsonObj, List<String> outputTemp){
        if(jsonObj.isEmpty()){
            return null;
        }
        RestHighLevelClient client= ESClientUtils.getClient();
        List<Map<String,Object>> resList=new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest();
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        searchRequest.indices(index);
        searchRequest.types(type);
        searchRequest.scroll(scroll);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        Set<String> keySets = jsonObj.keySet();
        List<String> set_to_list = new ArrayList<>(keySets);
        QueryBuilder queryBuilder=null;

        //获取index
        String[] index_arr = index.split("_");
        String termQuery="keyword";
        if(index_arr[1].equals("account")){
            termQuery+=".keyword";
        }
        if(keySets.size()>0){
            queryBuilder=QueryBuilders.termQuery(termQuery,jsonObj.get(set_to_list.get(0)));
        }else{
            queryBuilder=QueryBuilders.termQuery(termQuery,"");
        }

        searchSourceBuilder=searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(30);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String scrollId = searchResponse.getScrollId(); // 获取scrollId
        SearchHit[] searchHits = searchResponse.getHits().getHits();

        Map<String ,Object> resMap=new HashMap<>();//最终的map输出
        for(String temp:outputTemp){
            resMap.put(temp,null);//初始化为null
        }

        //处理返回的搜索结果
        for (SearchHit hit : searchHits) {
            Map<String, Object> map = hit.getSourceAsMap();
            //获取二级及之后的属性，如sample>file_type  ->  file_type
            String key = (String)map.get("key");
            String final_key=key.substring(key.indexOf(">")+1);
            if(resMap.containsKey(final_key)){
                String a[]=index.split("_");
                Template template = iTemplateService.loadByNameAndSrc(final_key, a[1]);
                if ("object".equals(template.getType())){
                    String dict_value =(String) map.get("dict_value");
                    dict_value = dict_value.replace("\"", "'").
                            replace("\\", "").
                            replace("['{","[{").replace("}']","}]");;
                    resMap.put(final_key,dict_value);
                }else if ("string".equals(template.getType())||"list".equals(template.getType())){
                    String value =(String) map.get("value");
                    String replaced_str = value.replace("\"", "'");
                    resMap.put(final_key,replaced_str);
                }
            }
        }

        while (searchHits != null && searchHits.length > 0) {
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll); //重新设置游标ID
            try {
                searchResponse = client.searchScroll(scrollRequest); //游标查询，这里使用searchScroll，与第一次查询不同
            } catch (IOException e) {
                e.printStackTrace();
            }
            scrollId = searchResponse.getScrollId(); //获取新的游标ID
            searchHits = searchResponse.getHits().getHits();

            //处理返回的搜索结果
            for (SearchHit hit : searchHits) {

                Map<String, Object> map = hit.getSourceAsMap();
                String key = (String)map.get("key");
                String final_key=key.substring(key.indexOf(">")+1);
                if(resMap.containsKey(final_key)){
                    String a[]=index.split("_");
                    Template template = iTemplateService.loadByNameAndSrc(final_key, a[1]);
                    if ("object".equals(template.getType())){
                        String dict_value =(String) map.get("dict_value");
                        dict_value = dict_value.replace("\"", "'").replace("\\", "").
                                replace("['{","[{").replace("}']","}]");
                        resMap.put(final_key,dict_value);
                    }else if ("string".equals(template.getType())||"list".equals(template.getType())){
                        String value =(String) map.get("value");
                        String replaced_str = value.replace("\"", "'");
                        resMap.put(final_key,replaced_str);
                    }
                }
            }
        }

        ClearScrollRequest clearScrollRequest = new ClearScrollRequest(); //一旦查询全部完成，清除游标
        clearScrollRequest.addScrollId(scrollId);
        ClearScrollResponse clearScrollResponse = null;
        try {
            clearScrollResponse = client.clearScroll(clearScrollRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean succeeded = clearScrollResponse.isSucceeded();
        ESClientUtils.close(client);
        resList.add(resMap);
        if(succeeded==true){
            return resList;
        }else{
            return null;
        }
    }


    @Override
    public   List<String> filterIncidentByTimeFrame(String index,String type,String start_time, String end_time) {
        RestHighLevelClient client= ESClientUtils.getClient();
        List<String> resList=new ArrayList<>();
        List<Incident> resObjList=new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest();
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        searchRequest.indices(index);
        searchRequest.types(type);
        searchRequest.scroll(scroll);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        RangeQueryBuilder rangequerybuilder = QueryBuilders
//                .rangeQuery("value")
//                .from(start_time).to(end_time);
        BoolQueryBuilder query = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("key", "事件发生时间"));
        searchSourceBuilder=searchSourceBuilder.query(query);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(30);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String scrollId = searchResponse.getScrollId(); // 获取scrollId
        SearchHit[] searchHits = searchResponse.getHits().getHits();

        //处理返回的搜索结果
        for (SearchHit hit : searchHits) {
            Map<String, Object> map = hit.getSourceAsMap();
            String value= (String) map.get("value");
            if(isInDateFrame(value,start_time,end_time)){
                String pattern=getTimePattern(value);
                resObjList.add(new Incident((String)map.get("keyword"),value,pattern));
            }
        }

        while (searchHits != null && searchHits.length > 0) {
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll); //重新设置游标ID
            try {
                searchResponse = client.searchScroll(scrollRequest); //游标查询，这里使用searchScroll，与第一次查询不同
            } catch (IOException e) {
                e.printStackTrace();
            }
            scrollId = searchResponse.getScrollId(); //获取新的游标ID
            searchHits = searchResponse.getHits().getHits();

            //处理返回的搜索结果
            for (SearchHit hit : searchHits) {
                Map<String, Object> map = hit.getSourceAsMap();
                String value= (String) map.get("value");
                if(isInDateFrame(value,start_time,end_time)){
                    String pattern=getTimePattern(value);
                    resObjList.add(new Incident((String)map.get("keyword"),value,pattern));
                }
            }
        }

        ClearScrollRequest clearScrollRequest = new ClearScrollRequest(); //一旦查询全部完成，清除游标
        clearScrollRequest.addScrollId(scrollId);
        ClearScrollResponse clearScrollResponse = null;
        try {
            clearScrollResponse = client.clearScroll(clearScrollRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean succeeded = clearScrollResponse.isSucceeded();
        ESClientUtils.close(client);
        if(succeeded==true){
            resList=sortTimeList(resObjList);
            return resList;
        }else{
            return null;
        }
    }

    private static List<String> sortTimeList(List<Incident> resObjList) {
        List<String> resList=new ArrayList<>();
        Collections.sort(resObjList, new Comparator<Incident>() {
            @Override
            public int compare(Incident o1, Incident o2) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat(o1.getTime_pattern());
                    Date dt1 = format.parse(o1.getStart_time());
                    SimpleDateFormat format2 = new SimpleDateFormat(o2.getTime_pattern());
                    Date dt2 = format2.parse(o2.getStart_time());
                    // 这是由大向小排序   如果要由小向大转换比较符号就可以
                    if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }

        });
        for(Incident i:resObjList){
            resList.add(i.getId());
        }
        return resList;
    }

    private static String getTimePattern(String time_str) {
        String pattern="";
        String MONTH_REGEX = "^([1-9]\\d{3}年)(([0]{0,1}[1-9]月)|([1][0-2]月))$";//2019年3月
        String YEAR_REGEX = "^([1-9]\\d{3}年)";//2019年3月
        String YEAR_REGEX2 = "^([1-9]\\d{3})";//2019年3
        // 验证日期格式为yyyy-MM-dd  yyyy-M-dd
        String regex = "^([1-9]\\d{3}-)(([0]{0,1}[1-9]-)|([1][0-2]-))(([0-3]{0,1}[0-9]))$";
        // 验证日期格式为dd/MM/yyyy
        String regex2 ="^(([0-3]{0,1}[0-9]/))(([0]{0,1}[1-9]/)|([1][0-2]-))([1-9]\\d{3})$";
        //验证日期格式为yyyy-MM-dd HH:mm:ss
        String regex3="^(((20[0-3][0-9]-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|(20[0-3][0-9]-(0[2469]|11)-(0[1-9]|[12][0-9]|30))) (20|21|22|23|[0-1][0-9]):[0-5][0-9]:[0-5][0-9])$";
        //check yyyy-MM
        String regex4="^([1-9]\\d{3}-)(([0]{0,1}[1-9])|([1][0-2]))$";
        String regex5="^([1-9]\\d{3}年)(([0]{0,1}[1-9]月)|([1][0-2]月))(([0-3]{0,1}[0-9]日))$";
        String regex6="^([1-9]\\d{3}年-)(([0]{0,1}[1-9]月-)|([1][0-2]月-))(([0-3]{0,1}[0-9]日))$";
        if(time_str.matches(MONTH_REGEX)){
            pattern="yyyy年MM月";
        }else if(time_str.matches(YEAR_REGEX)){
            pattern="yyyy年";
        }else if(time_str.matches(YEAR_REGEX2)){
            pattern="yyyy";
        }else if(time_str.matches(regex)){
            pattern="yyyy-MM-dd";
        }else if(time_str.matches(regex4)){
            pattern="yyyy-MM";
        } else if(time_str.matches(regex2)){
            pattern="dd/MM/yyyy";
        }else if(time_str.matches(regex3)){
            pattern="yyyy-MM-dd HH:mm:ss";
        }else if(time_str.matches(regex5)){
            pattern="yyyy年MM月dd日";
        }else if(time_str.matches(regex6)){
            pattern="yyyy年-MM月-dd日";
        }
        return pattern;
    }

    /**
     * 判断value是否在[start_time,end_time]内
     * @param value 指定格式串的时间
     * @param start_time 时间戳
     * @param end_time 时间戳
     * @return
     */
    private static boolean isInDateFrame(String value, String start_time, String end_time) {
        String s = time2timestamp(value);
        long ts=0l;
        if(s==""){
            return false;
        }else{
            ts=Long.valueOf(s);
        }
        long start=Long.valueOf(start_time);
        long end=Long.valueOf(end_time);
        if((ts>=start)&&(ts<=end)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 将指定字符串格式转化为时间戳
     * @param time_str
     * @return
     */
    public static String time2timestamp(String time_str) {
        String pattern="";
        String MONTH_REGEX = "^([1-9]\\d{3}年)(([0]{0,1}[1-9]月)|([1][0-2]月))$";//2019年3月
        String YEAR_REGEX = "^([1-9]\\d{3}年)";//2019年3月
        String YEAR_REGEX2 = "^([1-9]\\d{3})";//2019年3
        // 验证日期格式为yyyy-MM-dd  yyyy-M-dd
        String regex = "^([1-9]\\d{3}-)(([0]{0,1}[1-9]-)|([1][0-2]-))(([0-3]{0,1}[0-9]))$";
        // 验证日期格式为dd/MM/yyyy
        String regex2 ="^(([0-3]{0,1}[0-9]/))(([0]{0,1}[1-9]/)|([1][0-2]-))([1-9]\\d{3})$";
        //验证日期格式为yyyy-MM-dd HH:mm:ss
        String regex3="^(((20[0-3][0-9]-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|(20[0-3][0-9]-(0[2469]|11)-(0[1-9]|[12][0-9]|30))) (20|21|22|23|[0-1][0-9]):[0-5][0-9]:[0-5][0-9])$";
        //check yyyy-MM
        String regex4="^([1-9]\\d{3}-)(([0]{0,1}[1-9])|([1][0-2]))$";
        String regex5="^([1-9]\\d{3}年)(([0]{0,1}[1-9]月)|([1][0-2]月))(([0-3]{0,1}[0-9]日))$";
        String regex6="^([1-9]\\d{3}年-)(([0]{0,1}[1-9]月-)|([1][0-2]月-))(([0-3]{0,1}[0-9]日))$";
        if(time_str.matches(MONTH_REGEX)){
            pattern="yyyy年MM月";
        }else if(time_str.matches(YEAR_REGEX)){
            pattern="yyyy年";
        }else if(time_str.matches(YEAR_REGEX2)){
            pattern="yyyy";
        }else if(time_str.matches(regex)){
            pattern="yyyy-MM-dd";
        }else if(time_str.matches(regex4)){
            pattern="yyyy-MM";
        } else if(time_str.matches(regex2)){
            pattern="dd/MM/yyyy";
        }else if(time_str.matches(regex3)){
            pattern="yyyy-MM-dd HH:mm:ss";
        }else if(time_str.matches(regex5)){
            pattern="yyyy年MM月dd日";
        }else if(time_str.matches(regex6)){
            pattern="yyyy年-MM月-dd日";
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = simpleDateFormat.parse(time_str);
        } catch (ParseException e) {
            return "";
        }
        long ts = date.getTime();
        return String.valueOf(ts);
    }

    @Override
    public List<String>  reverseSearch(List<String> inputStrs, List<String> metadataNames,
                                       JSONObject jsonObj,String searchType) {
        if(jsonObj.isEmpty()){
            return null;
        }
        boolean flag=true;
        Set<String> keys = jsonObj.keySet();
        //与定义的模板是否相同，如果不相同，则返回null
        List<String> input_key = new ArrayList<>(keys);
        for(String t:input_key){
            if(!inputStrs.contains(t)){
                flag=false;
            }
        }
        if(flag==false){//定义与输入的key不相同
            return null;
        }
        //将每个字段在每个index中查询
        List<String> resList=searchPerIndexByAtt(index,type,jsonObj,searchType);
        return resList;
    }


    /**
     * 根据输入的属性及值查询索引
     * @param index
     * @param type
     * @param jsonObj
     * @return
     */
    private  List<String> searchPerIndexByAtt(String index, String type, JSONObject jsonObj, String searchType) {
        if(jsonObj.isEmpty()){
            return null;
        }
        Set<String> keys = jsonObj.keySet();
        List<String> keyList=new ArrayList<>(keys);
        RestHighLevelClient client= ESClientUtils.getClient();
        List<String> resList=new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest();
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        searchRequest.indices(index);
        searchRequest.types(type);
        searchRequest.scroll(scroll);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder query = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("key",keyList.get(0)));
        searchSourceBuilder=searchSourceBuilder.query(query);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(9999);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String scrollId = searchResponse.getScrollId(); // 获取scrollId
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        Set<String> temp_inp=new HashSet<String>();
        Set<String> temp_cli_inp=new HashSet<String>();//传递http请求中的输入

        //处理返回的搜索结果
        for (SearchHit hit : searchHits) {
            Map<String, Object> map = hit.getSourceAsMap();
            Template template =iTemplateService.loadByNameAndSrc(keyList.get(0), "incident");

            if ("object".equals(template.getType())){
                String dict_value =(String) map.get("dict_value");
                if(dict_value==null){
                    break;
                }
                dict_value = dict_value.replace("\"", "'").replace("\\", "").
                        replace("['{","[{").replace("}']","}]");
                //parse ES
                List<JSONObject> objects = (List<JSONObject>) JSONArray.parseArray(dict_value,JSONObject.class);
                for(JSONObject jsonObject:objects){
                    temp_inp.add((String)jsonObject.get("值"));
                    temp_inp.add((String)jsonObject.get("描述"));
                }
                //parse http input
                List<JSONObject> json_obj = (List<JSONObject>) JSONArray.parseArray((String)jsonObj.get(keyList.get(0)),JSONObject.class);
                for(JSONObject json:json_obj){
                    temp_cli_inp.add((String)json.get("值"));
                    temp_cli_inp.add((String)json.get("描述"));
                }
            }else if ("list".equals(template.getType())){
                String value =(String) map.get("value");
                if(value==null){
                    break;
                }
                value = value.replace("\"", "'").replace("\\", "");
                //parse ES
                List<String> objList = (List<String>)JSONArray.parseArray(value,String.class);
                for(String strObj:objList){
                    temp_inp.add(strObj);
                }
                //parse http input
                List<String> cli_inpu_obj = (List<String>)JSONArray.parseArray((String)jsonObj.get(keyList.get(0)),String.class);
                for(String cliObj:cli_inpu_obj){
                    temp_cli_inp.add(cliObj);
                }
            }else if ("string".equals(template.getType())){
                String value =(String) map.get("value");
                if(value==null){
                    break;
                }
                value = value.replace("\"", "'").replace("\\", "");
                //parse ES
                temp_inp.add(value);
                //parse http input
                temp_cli_inp.add(value);
            }

            if("1".equals(searchType)){//精确查询
                for(String s:temp_cli_inp){
                    if(temp_inp.contains(s)){
                        resList.add((String)map.get("keyword"));
                    }
                }
            }else{//模糊查询
                for(String s:temp_cli_inp){
                    for(String t:temp_inp){
                        if(t.contains(s)){
                            resList.add((String)map.get("keyword"));
                        }
                    }
                }
            }
        }

        while (searchHits != null && searchHits.length > 0) {
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll); //重新设置游标ID
            try {
                searchResponse = client.searchScroll(scrollRequest); //游标查询，这里使用searchScroll，与第一次查询不同
            } catch (IOException e) {
                e.printStackTrace();
            }
            scrollId = searchResponse.getScrollId(); //获取新的游标ID
            searchHits = searchResponse.getHits().getHits();

            //处理返回的搜索结果
            for (SearchHit hit : searchHits) {
                Map<String, Object> map = hit.getSourceAsMap();
                Template template =iTemplateService.loadByNameAndSrc(keyList.get(0), "incident");
                if ("object".equals(template.getType())){
                    String dict_value =(String) map.get("dict_value");
                    if(dict_value==null){
                        break;
                    }
                    dict_value = dict_value.replace("\"", "'").replace("\\", "").
                            replace("['{","[{").replace("}']","}]");
                    //parse ES
                    List<JSONObject> objects = (List<JSONObject>) JSONArray.parseArray(dict_value,JSONObject.class);
                    for(JSONObject jsonObject:objects){
                        temp_inp.add((String)jsonObject.get("值"));
                        temp_inp.add((String)jsonObject.get("描述"));
                    }
                    //parse http input
                    List<JSONObject> json_obj = (List<JSONObject>) JSONArray.parseArray((String)jsonObj.get(keyList.get(0)),JSONObject.class);
                    for(JSONObject json:json_obj){
                        temp_cli_inp.add((String)json.get("值"));
                        temp_cli_inp.add((String)json.get("描述"));
                    }
                }else if ("list".equals(template.getType())){
                    String value =(String) map.get("value");
                    if(value==null){
                        break;
                    }
                    value = value.replace("\"", "'").replace("\\", "");
                    //parse ES
                    List<String> objList = (List<String>)JSONArray.parseArray(value,String.class);
                    for(String strObj:objList){
                        temp_inp.add(strObj);
                    }
                    //parse http input
                    List<String> cli_inpu_obj = (List<String>)JSONArray.parseArray((String)jsonObj.get(keyList.get(0)),String.class);
                    for(String cliObj:cli_inpu_obj){
                        temp_cli_inp.add(cliObj);
                    }
                }else if ("string".equals(template.getType())){
                    String value =(String) map.get("value");
                    if(value==null){
                        break;
                    }
                    value = value.replace("\"", "'").replace("\\", "");
                    //parse ES
                    temp_inp.add(value);
                    //parse http input
                    temp_cli_inp.add(value);
                }

                if("1".equals(searchType)){//精确查询
                    for(String s:temp_cli_inp){
                        if(temp_inp.contains(s)){
                            resList.add((String)map.get("keyword"));
                        }
                    }
                }else{//模糊查询
                    for(String s:temp_cli_inp){
                        for(String t:temp_inp){
                            if(t.contains(s)){
                                resList.add((String)map.get("keyword"));
                            }
                        }
                    }
                }
            }
        }
        //根据incidentID查询整个Incident
        Set<String> keySet = jsonObj.keySet();
        List<String> outTemp=new ArrayList<>(keySet);
        Set<String> final_res_set=new HashSet<>();
        for(String ids:resList){
            JSONObject json = new JSONObject();
            json.put("incident",ids);
            List<Map<String, Object>> mapRes =iApiService.SearchNotPage(index, type, json, outTemp);
            if(matchIncident(mapRes,jsonObj,searchType)){ //判断各个字段是否与模板及值一致，如果不一致，移除resList的incident ID
                final_res_set.add(ids);
            }
        }

        ClearScrollRequest clearScrollRequest = new ClearScrollRequest(); //一旦查询全部完成，清除游标
        clearScrollRequest.addScrollId(scrollId);
        ClearScrollResponse clearScrollResponse = null;
        try {
            clearScrollResponse = client.clearScroll(clearScrollRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean succeeded = clearScrollResponse.isSucceeded();
        ESClientUtils.close(client);
        if(succeeded==true){
            List<String> res=new ArrayList<>(final_res_set);
            List<Incident> incidents=new ArrayList<>();
            for(String r:res){
                Incident incident= getIncidentHappenTime(index, type, r);
                if(incident.getStart_time()!=""){
                    incidents.add(incident);
                }
            }
            return  sortTimeList(incidents);
        }else{
            return null;
        }
    }

    private Incident getIncidentHappenTime(String index, String type, String incidentID) {
        RestHighLevelClient client= ESClientUtils.getClient();
        Incident incident  =null;
        SearchRequest searchRequest = new SearchRequest();
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        searchRequest.indices(index);
        searchRequest.types(type);
        searchRequest.scroll(scroll);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder query = QueryBuilders.boolQuery().must(
                QueryBuilders.termQuery("key", "事件发生时间")).must(
                QueryBuilders.termQuery("keyword", incidentID));
        searchSourceBuilder=searchSourceBuilder.query(query);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(30);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String scrollId = searchResponse.getScrollId(); // 获取scrollId
        SearchHit[] searchHits = searchResponse.getHits().getHits();

        //处理返回的搜索结果
        for (SearchHit hit : searchHits) {
            Map<String, Object> map = hit.getSourceAsMap();
            String value= (String) map.get("value");
            String pattern=getTimePattern(value);
            incident= new Incident((String) map.get("keyword"), value, pattern);
        }

        ClearScrollRequest clearScrollRequest = new ClearScrollRequest(); //一旦查询全部完成，清除游标
        clearScrollRequest.addScrollId(scrollId);
        ClearScrollResponse clearScrollResponse = null;
        try {
            clearScrollResponse = client.clearScroll(clearScrollRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean succeeded = clearScrollResponse.isSucceeded();
        ESClientUtils.close(client);
        if(succeeded==true){
            return incident;
        }else{
            return null;
        }

    }

    private  boolean matchIncident(List<Map<String, Object>> mapRes, JSONObject jsonObj,String searchType) {
        Set<String> keySet = jsonObj.keySet();
        boolean flag=false;//匹配
        for(String item:keySet){
            flag=false;
            for(Map<String,Object> m:mapRes){
                String value =(String) m.get(item);
                if(value==null){
                    break;
                }
                Set<String> temp_inp=new HashSet<>();
                Set<String> temp_cli_inp=new HashSet<>();
                Template template = iTemplateService.loadByNameAndSrc(item, "incident");
                if ("object".equals(template.getType())){
                    value = value.replace("\"", "'").replace("\\", "").
                            replace("['{","[{").replace("}']","}]");

                    List<JSONObject> objects = (List<JSONObject>) JSONArray.parseArray(value,JSONObject.class);
                    for(JSONObject jsonObject:objects){
                        temp_inp.add((String)jsonObject.get("值"));
                        temp_inp.add((String)jsonObject.get("描述"));
                    }

                    //parse http input
                    List<JSONObject> json_obj = (List<JSONObject>) JSONArray.parseArray((String)jsonObj.get(item),JSONObject.class);
                    for(JSONObject json:json_obj){
                        temp_cli_inp.add((String)json.get("值"));
                        temp_cli_inp.add((String)json.get("描述"));
                    }

                }else if ("list".equals(template.getType())){
                    value = value.replace("\"", "'").replace("\\", "");
                    List<String> objList = (List<String>)JSONArray.parseArray(value,String.class);
                    for(String strObj:objList){
                        temp_inp.add(strObj);
                    }

                    //parse http input
                    List<String> cli_inpu_obj = (List<String>)JSONArray.parseArray((String)jsonObj.get(item),String.class);
                    for(String cliObj:cli_inpu_obj){
                        temp_cli_inp.add(cliObj);
                    }
                }else if("string".equals(template.getType())){
                    value = value.replace("\"", "'").replace("\\", "");
                    temp_inp.add(value);
                    temp_cli_inp.add(value);
                }

                if("1".equals(searchType)){//精确查询
                    for(String s:temp_cli_inp){
                        if(temp_inp.contains(s)){
                            flag=true;
                        }
                    }
                }else{
                    for(String s:temp_cli_inp){
                        for(String t:temp_inp){
                            if(t.contains(s)){
                                flag=true;
                            }
                        }
                        if(flag==true){
                            break;
                        }
                    }
                }
            }
            if(flag==false){
                return flag;
            }
        }

        return flag;
    }
}


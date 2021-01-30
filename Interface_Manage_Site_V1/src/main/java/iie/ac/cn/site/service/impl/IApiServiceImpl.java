package iie.ac.cn.site.service.impl;

import com.alibaba.fastjson.JSONObject;
import iie.ac.cn.site.dao.ApiMapper;
import iie.ac.cn.site.model.Api;
import iie.ac.cn.site.model.ApiExample;
import iie.ac.cn.site.model.Template;
import iie.ac.cn.site.service.IApiService;
import iie.ac.cn.site.service.ITemplateService;
import iie.ac.cn.site.util.ESClientUtils;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;


@Service
public class IApiServiceImpl implements IApiService {


    @Autowired
    private ApiMapper ApiMapper;
    @Autowired
    private ITemplateService iTemplateService;

    public List<Api> query() {
        ApiExample example = new ApiExample();
        List<Api> apiList = this.ApiMapper.selectByExample(example);
        return apiList;
    }

    /**
     * 总数
     *
     * @return
     */
    @Override
    public long total() {
        ApiExample example = new ApiExample();
        return this.ApiMapper.countByExample(example);
    }

    /**
     * 查询数量
     * @param name
     * @return
     */
    @Override
    public long countByName(String name) {
        ApiExample example = new ApiExample();
        example.createCriteria().andNameEqualTo(name);
        return this.ApiMapper.countByExample(example);

    }

    @Override
    public long countByLikelyName(String likelyName) {
        ApiExample example = new ApiExample();
        example.createCriteria().andNameLike("%" + likelyName + "%");
        return this.ApiMapper.countByExample(example);
    }

    /**
     * 分页查询
     *
     * @param rowIndex
     * @param pageSize
     * @return
     */
    @Override
    public List<Api> query(long rowIndex, int pageSize) {

        ApiExample example = new ApiExample();

        example.setRowIndex(rowIndex);
        example.setPageSize(pageSize);

        List<Api> apiList = this.ApiMapper.selectByExample(example);

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
    public List<Api> queryByName(String name, long rowIndex, int pageSize, String orderByClause) {

        ApiExample example = new ApiExample();
        example.setRowIndex(rowIndex);
        example.setPageSize(pageSize);
        example.createCriteria().andNameEqualTo(name);
        if (null != orderByClause) {
            example.setOrderByClause(orderByClause);
        }
        List<Api> apiList = this.ApiMapper.selectByExample(example);

        return apiList;
    }

    @Override
    public List<Api> queryByLikelyName(String name, long rowIndex, int pageSize, String orderByClause) {
        ApiExample example = new ApiExample();
        example.setRowIndex(rowIndex);
        example.setPageSize(pageSize);
        example.createCriteria().andNameLike("%" + name + "%");
        if (null != orderByClause) {
            example.setOrderByClause(orderByClause);
        }
        List<Api> apiList = this.ApiMapper.selectByExample(example);


        return apiList;
    }


    @Override
    public Api loadByName(String name) {
        ApiExample example = new ApiExample();
        example.createCriteria().andNameEqualTo(name);
        List<Api> apiList = this.ApiMapper.selectByExample(example);
        return apiList.isEmpty() ? null : apiList.get(0);
    }

    /**
     * 根据名称列出得到API列表
     * @param name
     * @return
     */
    @Override
    public List<Api> query(String name) {
        ApiExample example = new ApiExample();
        example.createCriteria().andNameEqualTo(name);
        List<Api> apiList = this.ApiMapper.selectByExample(example);
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
    public int insert(Api api) {
        return this.ApiMapper.insert(api);

    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Override
    public Api loadById(int id) {
        ApiExample example = new ApiExample();
        example.createCriteria().andIdEqualTo(id);

        List<Api> apiList = this.ApiMapper.selectByExample(example);

        return apiList.isEmpty() ? null : apiList.get(0);

    }

    /**
     * 根据ID更新
     *
     * @param api
     */
    @Override
    public int update(Api api) {
        ApiExample example = new ApiExample();
        example.createCriteria().andIdEqualTo(api.getId());
        return this.ApiMapper.updateByExample(api, example);
    }

    /**
     * 根据ID删除
     *
     * @param id
     */
    @Override
    public int delete(int id) {
        ApiExample example = new ApiExample();
        example.createCriteria().andIdEqualTo(id);
        return this.ApiMapper.deleteByExample(example);
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

    /**
     * 获取list列表中的第一个元素:['a1','a2','a3']  -> a1
     * @param value
     * @return
     */
    private String getListFirst(Object value) {
        String temp_str=(String)value;
        int start_pos = temp_str.indexOf("[");
        int end_pos = temp_str.lastIndexOf("]");
        String temp_cut_string = temp_str.substring(start_pos + 1, end_pos);
        String[] split = temp_cut_string.split(",");
        if(split.length>0){
            return  split[0];
        }else{
            return "";
        }
    }
}
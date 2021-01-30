package iie.ac.cn.site.service.impl;

import iie.ac.cn.site.dao.TemplateMapper;
import iie.ac.cn.site.model.Template;
import iie.ac.cn.site.model.TemplateExample;
import iie.ac.cn.site.service.ITemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class TemplateServiceImpl implements ITemplateService {

    @Autowired
    private TemplateMapper templateMapper;


    public List<Template> query() {
        TemplateExample example = new TemplateExample();
        List<Template> templateList = this.templateMapper.selectByExample(example);

        return templateList;
    }

    /**
     * 总数
     * @return
     */
    @Override
    public long total() {
        TemplateExample example = new TemplateExample();

        return this.templateMapper.countByExample(example);
    }
    /**
     * 查询数量
     * @param nameLike
     * @return
     */
    @Override
    public long countByName(String nameLike) {
        TemplateExample example = new TemplateExample();
        example.createCriteria().andNameLike("%" + nameLike + "%");
        return this.templateMapper.countByExample(example);
    }

    /**
     * 分页查询
     *
     * @param rowIndex
     * @param pageSize
     * @return
     */
    @Override
    public List<Template> query(long rowIndex, int pageSize) {

        TemplateExample example = new TemplateExample();

        example.setRowIndex(rowIndex);
        example.setPageSize(pageSize);

        List<Template> templateList = this.templateMapper.selectByExample(example);

        return templateList;
    }


    /**
     * 分页查询
     *
     * @param nameLike 标题
     * @param rowIndex 开始记录索引
     * @param pageSize 查询数量
     * @return
     */
    @Override
    public List<Template> queryByName(String nameLike, long rowIndex, int pageSize) {

        return this.queryByName(nameLike, rowIndex, pageSize, null);
    }


    /**
     * @param nameLike
     * @param rowIndex
     * @param pageSize
     * @param orderByClause 排序信息
     * @return
     */
    @Override
    public List<Template> queryByName(String nameLike, long rowIndex, int pageSize, String orderByClause) {

        TemplateExample example = new TemplateExample();
        example.setRowIndex(rowIndex);
        example.setPageSize(pageSize);
        example.createCriteria().andNameLike("%" + nameLike + "%");
        if (null != orderByClause) {
            example.setOrderByClause(orderByClause);
        }


        List<Template> templateList = this.templateMapper.selectByExample(example);

        return templateList;
    }


    @Override
    public Template loadByNameAndSrc(String name,String src) {
        TemplateExample example = new TemplateExample();

        example.createCriteria().andNameEqualTo(name).andSrcEqualTo(src);
        List<Template> templateList = this.templateMapper.selectByExample(example);

        return templateList.isEmpty() ? null : templateList.get(0);
    }

    @Override
    public List<Template> queryDistinctSrc() {
        TemplateExample example = new TemplateExample();
        List<Template> templateList = this.templateMapper.selectByExample(example);
        Map<String,Template> map=new HashMap<>();
        if(templateList!=null){
            for(Template t:templateList){
                map.put(t.getSrc(),t);
            }
            Set<String> keySet = map.keySet();
            List<Template> templates=new ArrayList<>();
            for(String key:keySet){
                templates.add(map.get(key));
            }
            return templates;
        }
        return null;
    }

    /**
     * @param src
     * @return
     */
    @Override
    public List<Template> queryBySrc(String src) {
        TemplateExample example = new TemplateExample();
        example.createCriteria().andSrcEqualTo(src);
        List<Template> templateList = this.templateMapper.selectByExample(example);
        return templateList;
    }

    @Override
    public List<Template> queryByName(String name) {
        TemplateExample example = new TemplateExample();
        example.createCriteria().andNameEqualTo(name);
        List<Template> templateList = this.templateMapper.selectByExample(example);
        return templateList;
    }

    @Override
    public List<Template> queryByNameAndSrc(String name, String src) {
        TemplateExample example = new TemplateExample();
        example.createCriteria().andSrcEqualTo(src).andNameEqualTo(name);
        List<Template> templateList = this.templateMapper.selectByExample(example);
        return templateList;
    }

    @Override
    public List<Template> queryByTypeAndSrc(String type, String src) {
        TemplateExample example = new TemplateExample();
        example.createCriteria().andSrcEqualTo(src).andTypeEqualTo(type);
        List<Template> templateList = this.templateMapper.selectByExample(example);
        return templateList;
    }

    @Override
    public List<Template> queryAllDistinctName() {
        TemplateExample example = new TemplateExample();
        List<Template> templateList = this.templateMapper.selectByExample(example);
        List<Template> templates=new ArrayList<>();
        List<String> names=new ArrayList<>();
        for(Template t:templateList){
            if(!names.contains(t.getName())){
                templates.add(t);
                names.add(t.getName());
            }
        }
        return templates;
    }

    @Override
    public List<Template> queryByAtt(String name) {
        TemplateExample example = new TemplateExample();
        example.createCriteria().andNameEqualTo(name);
        List<Template> templateList = this.templateMapper.selectByExample(example);
        return templateList.isEmpty() ? null : templateList;

    }


    /**
     * 插入
     *
     * @param template
     */
    @Override
    public int insert(Template template) {

        return this.templateMapper.insert(template);

    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Override
    public Template loadById(int id) {
        TemplateExample example = new TemplateExample();
        example.createCriteria().andIdEqualTo(id);

        List<Template> templateList = this.templateMapper.selectByExample(example);

        return templateList.isEmpty() ? null : templateList.get(0);

    }

    /**
     * 根据ID更新
     *
     * @param template
     */
    @Override
    public int update(Template template) {
        TemplateExample example = new TemplateExample();
        example.createCriteria().andIdEqualTo(template.getId());
        return this.templateMapper.updateByExample(template, example);
    }

    /**
     * 根据ID删除
     *
     * @param id
     */
    @Override
    public int delete(int id) {
        TemplateExample example = new TemplateExample();
        example.createCriteria().andIdEqualTo(id);
        return this.templateMapper.deleteByExample(example);
    }

}
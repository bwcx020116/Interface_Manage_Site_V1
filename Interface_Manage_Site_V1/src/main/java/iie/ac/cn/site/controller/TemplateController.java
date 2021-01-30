package iie.ac.cn.site.controller;

import iie.ac.cn.site.common.BaseResult;
import iie.ac.cn.site.common.BootstrapTable;
import iie.ac.cn.site.common.QueryParam;
import iie.ac.cn.site.model.Template;
import iie.ac.cn.site.model.User;
import iie.ac.cn.site.service.ITemplateService;
import iie.ac.cn.site.service.IUserService;
import iie.ac.cn.site.util.PropertyUtil;
import iie.ac.cn.site.vo.ImportVo;
import iie.ac.cn.site.vo.LoginParam;
import iie.ac.cn.site.vo.LoginParam2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/template")
public class TemplateController {
    private static final Log logger = LogFactory
            .getLog(TemplateController.class);
    @Autowired
    private ITemplateService templateService;
    /**
     * 模板首页
     *
     * @param model
     * @return
     */
    @RequestMapping({"index.html", ""})
    public String index(Model model) {


        return "template/index";
    }

    /**
     * 分页查询
     *
     * @param queryParam 查询条件
     * @return
     */

    @RequestMapping("query_by_page.json")
    @ResponseBody
    public BootstrapTable queryByPage(@RequestBody(required = false) final QueryParam queryParam

    ) {
        //查询的关键词
        String search = String.valueOf(queryParam.getSearch().get("searchLike"));
        //根据页码计算开始记录索引
        long pageIndex = (queryParam.getPageNumber() - 1) * queryParam.getPageSize();
        //根据当前查询条件，查询符合条件的记录数
        long total = this.templateService.countByName(search);

        String orderByClause = PropertyUtil.toUnderline(queryParam.getSortName()) + " " + queryParam.getSortOrder();

        List<Template> templateList = this.templateService.queryByName(search, pageIndex, queryParam.getPageSize(), orderByClause);


        return new BootstrapTable(total, templateList);

    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping({"new.html"})
    public String toNew(Model model) {
        Template template = new Template();
        model.addAttribute("template", template);
        return "template/new";
    }

    /**
     * 编辑页面
     *
     * @param model
     * @param id    ID
     * @return
     */
    @RequestMapping({"{id}/edit.html"})
    public String toEdit(Model model, @PathVariable final int id) {
        //根据ID查询元数据信息
        Template template = this.templateService.loadById(id);
        model.addAttribute("template", template);
        return "template/new";
    }


    /**
     * 导入模板页面
     *
     * @param model
     * @return
     */
    @RequestMapping({"import_template.html"})
    public String toImportTemplate(Model model) {
       // List<Template> templates = this.templateService.queryDistinctSrc();
      //  model.addAttribute("templateList",templates);
        return "template/import_template";
    }


    /**
     * 新增或更新
     * @param request
     * @param response
     * @param template
     * @return
     */
    @RequestMapping("save.json")
    @ResponseBody
    public BaseResult save(HttpServletRequest request,
                           HttpServletResponse response, @RequestBody Template template) {
        int count = 0;
        //如果templateID为0或null,需要增加,否则根据ID更新
        if (template.getId() == null || template.getId() == 0) {
            //将元数据添加到数据库中
            count = this.templateService.insert(template);
        } else {
            //根据ID更新元数据
            count = this.templateService.update(template);
        }
        BaseResult result = new BaseResult();
        result.setSuccess(count > 0 ? true : false);
        return result;
    }


    /**
     * 根据ID删除
     *
     * @param id
     * @return
     */
    @RequestMapping("delete.json")
    @ResponseBody
    public BaseResult delete(@RequestParam final int id) {
        //根据ID删除元数据
        int count = this.templateService.delete(id);
        BaseResult result = new BaseResult();
        //如果删除成功返回客户端true,否则返回false
        result.setSuccess(count > 0 ? true : false);
        return result;
    }

    @RequestMapping("import.json")
    @ResponseBody
    public BaseResult importFile(@RequestBody ImportVo importVo) {
        List<String> lines = new ArrayList<String>();
        try {
            lines = FileUtils.readLines(new File(importVo.getFileDir()), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BaseResult result = new BaseResult();
        if(lines.size()<1){
            result.setSuccess(false);
            return result;
        }
        for (String line : lines) {
            if(line==" "||"".equals(line)||line==null){
                continue;
            }
            String str = line.trim().replace("\"", "");
            String name="";
            String type="";
            if(str.contains(":")){
                 name=str.split(":")[0];
                 type=str.split(":")[1];
            }else if(str.contains(",")){
                name=str.split(",")[0];
                type=str.split(",")[1];
            }
            Template template = this.templateService.loadByNameAndSrc(name,importVo.getSrc());
            if (template!=null) {
                continue;
            }
            Template temp = new Template();
            temp.setName(name);
            temp.setType(type);
            temp.setSrc(importVo.getSrc());//通过前端获取的ip、url、sample

            int insert = this.templateService.insert(temp);

            if(insert==0){
                result.setSuccess(false);
                return result;
            }
        }
        result.setSuccess(true);
        return result;

    }


    @RequestMapping("export.json")
    @ResponseBody
    public BaseResult exportFile(final String stopWordDir) {
        List<Template> templateList = this.templateService.queryBySrc("ext");
        BaseResult result = new BaseResult();
        result.setSuccess(true);
        return result;
    }

}

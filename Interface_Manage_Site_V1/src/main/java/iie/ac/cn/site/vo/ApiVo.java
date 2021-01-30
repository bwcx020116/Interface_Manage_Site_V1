package iie.ac.cn.site.vo;

import iie.ac.cn.site.model.Api;

import java.util.List;

public class ApiVo extends Api {
    private List<String> inputParam;
    private List<String> outputParam;

    public List<String> getInputParam() {
        return inputParam;
    }

    public void setInputParam(List<String> inputParam) {
        this.inputParam = inputParam;
    }

    public List<String> getOutputParam() {
        return outputParam;
    }

    public void setOutputParam(List<String> outputParam) {
        this.outputParam = outputParam;
    }

    public ApiVo(Integer id, String name, String description, String metadata, String type,List<String> inputParam,
                 List<String> outputParam) {
        super(id, name, description, metadata,type);
        this.inputParam = inputParam;
        this.outputParam = outputParam;
    }

    public ApiVo() {
    }
}

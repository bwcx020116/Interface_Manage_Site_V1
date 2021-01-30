package iie.ac.cn.site.vo;

import iie.ac.cn.site.model.Api;
import iie.ac.cn.site.model.Reverseapi;

import java.util.List;

public class ReverseapiVo extends Reverseapi {
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

    public ReverseapiVo(String name, String description, String attribution, String apitype, String searchtype, List<String> inputParam, List<String> outputParam) {
        super(name, description, attribution, apitype, searchtype);
        this.inputParam = inputParam;
        this.outputParam = outputParam;
    }

    public ReverseapiVo(Integer id, String name, String description, String attribution, String apitype, String searchtype, List<String> inputParam, List<String> outputParam) {
        super(id, name, description, attribution, apitype, searchtype);
        this.inputParam = inputParam;
        this.outputParam = outputParam;
    }

    public ReverseapiVo(List<String> inputParam, List<String> outputParam) {
        this.inputParam = inputParam;
        this.outputParam = outputParam;
    }

    public ReverseapiVo() {
    }
}

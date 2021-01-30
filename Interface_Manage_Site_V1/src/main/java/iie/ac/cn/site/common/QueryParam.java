package iie.ac.cn.site.common;


import java.util.Map;

/**
 * BootStrap Table查询条件
 */
public class QueryParam {
    //每页显示数量
    private int pageSize;
    //当前页码
    private int pageNumber;
    //排序字段
    private String sortName;
    //排序顺序
    private String sortOrder;

    private Map<String, Object> search;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Map<String, Object> getSearch() {
        return search;
    }

    public void setSearch(Map<String, Object> search) {
        this.search = search;
    }


}

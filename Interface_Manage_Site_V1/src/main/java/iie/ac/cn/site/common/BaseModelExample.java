package iie.ac.cn.site.common;

/**
 * 扩展MyBatis自动生成的Example，添加分页功能
 */
public class BaseModelExample {
    //开始记录索引
    protected Long rowIndex;
    //每页显示的数量
    protected Integer pageSize;

    public Long getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Long rowIndex) {
        this.rowIndex = rowIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}

package iie.ac.cn.site.common;

import java.util.ArrayList;
import java.util.List;

/**
 * BootstrapTable 返回数据格式
 */
public class BootstrapTable {

    //总记录数
    private long total;
    //数据
    private List<?> rows = new ArrayList<Object>();

    public BootstrapTable(long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

}

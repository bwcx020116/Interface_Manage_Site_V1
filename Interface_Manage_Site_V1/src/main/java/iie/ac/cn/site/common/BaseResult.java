package iie.ac.cn.site.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 返回结果
 */
public class BaseResult {
    private static final Log logger = LogFactory.getLog(BaseResult.class);
    //返回编码
    private String resultCode = "";
    //返回消息
    private String resultMsg = "";
    //是否成功
    private boolean isSuccess;
    //返回的数据
    private Object data;

    public static Log getLogger() {
        return logger;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

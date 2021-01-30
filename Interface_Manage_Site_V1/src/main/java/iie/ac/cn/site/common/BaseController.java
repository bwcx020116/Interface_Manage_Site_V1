package iie.ac.cn.site.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

/**
 * 公共Controller类
 */
@Controller
public class BaseController {

    private static final Log logger = LogFactory.getLog(BaseController.class);

    /**
     * TODO: 获取登录用户ID
     * @return
     */
    protected int getLoginUserId() {
        return 0;
    }

}

package iie.ac.cn.site.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {


    private static final Log logger = LogFactory
            .getLog(LoginInterceptor.class);

    /**
     * 调用方法前拦截
     * @param request
     * @param response
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String uri = request.getRequestURI().toString();

        String url = request.getRequestURL().toString();
        logger.debug(" preHandle： 请求的uri为："+uri);
        logger.debug(" preHandle： 请求的url为："+url);


        //不用登录也可以访问的页面，直接跳过

        if(uri.equals("/") || uri.equals("/login.html")){
            return true;
        }
        //TODO: user为session key
        if(null==request.getSession().getAttribute("user")){

            //response.sendRedirect("/login.html");
            logger.debug("未登录...");
            response.sendRedirect("/err/nologin.html");
            return false;


        }
        return true;
    }

    /**
     * 调用方法以后拦截
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.debug("postHandle： ");
    }

    /**
     * 完成以后
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        logger.debug("afterCompletion： ");
    }
}

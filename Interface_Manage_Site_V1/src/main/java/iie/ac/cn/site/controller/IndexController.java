package iie.ac.cn.site.controller;

import iie.ac.cn.site.model.User;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@Controller
@RequestMapping("/")
public class IndexController {

    private static final Log logger = LogFactory
            .getLog(IndexController.class);

    @RequestMapping(value = {"","index.html"})
    public String toIndex(HttpServletRequest request,
                          HttpServletResponse response, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if(user==null)
        {
            return  "user/login";
        }else{
            return "template/index";
        }
    }

}

package iie.ac.cn.site.controller;


import iie.ac.cn.site.common.BaseResult;
import iie.ac.cn.site.model.Permit;
import iie.ac.cn.site.model.User;
import iie.ac.cn.site.service.IRolePermitService;
import iie.ac.cn.site.service.IUserService;
import iie.ac.cn.site.vo.LoginParam;
import iie.ac.cn.site.vo.LoginParam2;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {
    private static final Log logger = LogFactory
            .getLog(UserController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private IRolePermitService rolePermitService;

    @RequestMapping(value = {"","login.html"})
    public String index(HttpServletRequest request,
                        HttpServletResponse response) {
        return "user/login";
    }
    @RequestMapping(value = {"login.json"})
    @ResponseBody
    public BaseResult toLogin(HttpServletRequest request,
                              HttpServletResponse response,@RequestBody LoginParam loginParam) {
        BaseResult baseResult = new BaseResult();
        User user = userService.query(loginParam.getLoginName(), loginParam.getLoginPwd());
        if(user==null){
            baseResult.setSuccess(false);
            baseResult.setResultCode("loginNameErr");
            return  baseResult;
        }
        baseResult.setSuccess(true);
        List<Permit> permitList = rolePermitService.queryByRoleId(user.getRoleId());
        request.getSession().setAttribute("permitList", permitList);
        request.getSession().setAttribute("user",user);
        return baseResult;
    }
    @RequestMapping(value = {"register.json"})
    @ResponseBody
    public BaseResult toRegister(HttpServletRequest request,
                              HttpServletResponse response,@RequestBody LoginParam loginParam) {
        BaseResult baseResult = new BaseResult();
        User user=null;
        if(loginParam.getLoginName().contains("admin")){
            user=new User(loginParam.getLoginName(),loginParam.getLoginPwd(),1);
        }else{
            user=new User(loginParam.getLoginName(),loginParam.getLoginPwd(),2);
        }
        long l = userService.countByLoginName(loginParam.getLoginName());//用户名是唯一值
        int res=0;
        if(l!=0){
           baseResult.setSuccess(false);
           return baseResult;
        }else{
            int insert = userService.insert(user);
            if(insert!=1)
            {
                baseResult.setSuccess(false);
                return baseResult;
            }else{
                baseResult.setSuccess(true);
            }
        }
        return  baseResult;
    }

    /**
     * 修改用户密码
     * @param request
     * @param response
     * @param loginParam
     * @return
     */
    @RequestMapping(value = {"updateUser.json"})
    @ResponseBody
    public BaseResult toEditUserPwd(HttpServletRequest request,
                                 HttpServletResponse response,@RequestBody LoginParam2 loginParam) {
        BaseResult baseResult = new BaseResult();
        User user=new User(loginParam.getLoginName(),loginParam.getLoginPwd());
        if(!loginParam.getEnsurePwd().equals(loginParam.getLoginPwd())){
            baseResult.setSuccess(false);
            return baseResult;
        }else{
            User user1 = (User) request.getSession().getAttribute("user");
            User query = userService.query(user1.getLoginName(), user1.getLoginPwd());

            int update = userService.update(new User(query.getId(),loginParam.getLoginName(),loginParam.getLoginPwd(),query.getRoleId()));
            if(update==1){
                request.getSession().removeAttribute("user");
                baseResult.setSuccess(true);
            }else{
                baseResult.setSuccess(false);
            }
        }
        return  baseResult;
    }

    /**
     * 更新用户个人信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"updateProfile.html"})
    public String toUpdateProfile(HttpServletRequest request,
                           HttpServletResponse response) {
        return "user/updateProfile";
    }
    /**
     * 注册
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"register.html"})
    public String register(HttpServletRequest request,
                        HttpServletResponse response) {
        return "user/register";
    }

    @RequestMapping(value = {"logout.html"})
    public String toLogout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");
        return "user/login";
    }
    @RequestMapping(value = {"profile.html"})
    public String toProfile(HttpServletRequest request, HttpServletResponse response, Model model){
        User user= (User) request.getSession().getAttribute("user");
        model.addAttribute("user",user);
        return "user/profile";
    }

}

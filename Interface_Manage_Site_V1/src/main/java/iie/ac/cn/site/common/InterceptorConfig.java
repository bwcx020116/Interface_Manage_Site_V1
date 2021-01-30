package iie.ac.cn.site.common;

import org.apache.zookeeper.Login;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    /**
     * 注册自定义拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //  /*.html--->index.html  /**/*.html --> /channel/index.html
       registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/configure/*.html")
               .addPathPatterns("/fussion/*.html")
               .addPathPatterns("/search/*.html")
               .addPathPatterns("/template/*.html")
               .addPathPatterns("/user/updateProfile.html")
               .addPathPatterns("/user/profile.html")
               .addPathPatterns("/*.html");
    }
}

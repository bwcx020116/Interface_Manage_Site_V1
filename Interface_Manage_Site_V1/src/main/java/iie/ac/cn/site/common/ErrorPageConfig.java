package iie.ac.cn.site.common;


import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * 错误页面
 */
@Configuration
public class ErrorPageConfig {
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(
                    ConfigurableEmbeddedServletContainer container) {

                ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND,
                        "/err/404.html");
                ErrorPage errorPage500 = new ErrorPage(
                        HttpStatus.INTERNAL_SERVER_ERROR, "/err/500.html");
                container.addErrorPages(errorPage404,
                        errorPage500);
            }
        };
    }
}
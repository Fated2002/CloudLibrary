package com.demo.config;


//import com.zmz.interceptor.ResourcesInterceptor;
import com.demo.interceptor.ResourceInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

//SpringMVC的相关配置
@Configuration
/*@PropertySource("classpath:ignoreUrl.properties")*/
//等同于<context:component-scan base-package="com.itheima.controller"/>
@ComponentScan({"com.zl.controller"})
/*@Import({MyWebMvcConfig.class})*/
@EnableWebMvc
public class SpringMvcConfig  implements WebMvcConfigurer {
    /* @Value("#{'${ignoreUrl}'.split(',')}")
       private List<String> ignoreUrl;
     /*@Bean
       public ResourcesInterceptor resourcesInterceptor(){
           return new ResourcesInterceptor(ignoreUrl);
       }*/
    /*
     * 在注册的拦截器类中添加自定义拦截器
     * addPathPatterns()方法设置拦截的路径
     * excludePathPatterns()方法设置不拦截的路径
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ResourceInterceptor()).addPathPatterns("/**").excludePathPatterns("/css/**","/js/**","/img/**");
    }

     //开启静态资源的释放
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    //配置视图解析器
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/admin/",".jsp");
    }

}





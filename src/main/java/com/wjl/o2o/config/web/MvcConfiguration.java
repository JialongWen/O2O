package com.wjl.o2o.config.web;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.wjl.o2o.interceptor.shop.ShopLoginInterceptor;
import com.wjl.o2o.interceptor.shop.ShopPermissionInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.ServletException;

@Configuration
//等价于<mvc:annotation-driven/>
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {
    //spring容器
    @Autowired
    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 静态资源的配置
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/resources/WEB-INF/**").addResourceLocations("/WEB-INF/");
       registry.addResourceHandler("/upload/**").addResourceLocations("file:/home/o2o/image/upload/");
       // registry.addResourceHandler("/upload/**").addResourceLocations("file:D:/projectdev/image/upload/");
    }

    /**
     *定义默认的请求处理器
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean(name = "viewResolver")
    public InternalResourceViewResolver createViewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    //设置spring容器
        viewResolver.setApplicationContext(this.applicationContext);
        //取消缓存
        viewResolver.setCache(false);
        //设置解析的前缀
        viewResolver.setPrefix("/WEB-INF/html/");
        //设置视图解析的后座
        viewResolver.setSuffix(".html");
        return viewResolver;
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver createCommonsMultipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        commonsMultipartResolver.setMaxUploadSize(20971520);
        commonsMultipartResolver.setMaxInMemorySize(20971520);
        return commonsMultipartResolver;
    }
    @Value("${kaptcha.border}")
    private String border;
    @Value("${kaptcha.textproducer.font.color}")
    private String fcolor;
    @Value("${kaptcha.image.width}")
    private String width;
    @Value("${kaptcha.textproducer.char.string}")
    private String cString;
    @Value("${kaptcha.image.height}")
    private String height;
    @Value("${kaptcha.textproducer.font.size}")
    private String fsize;
    @Value("${kaptcha.noise.color}")
    private String nColor;
    @Value("${kaptcha.textproducer.char.length}")
    private String clength;
    @Value("${kaptcha.textproducer.font.names}")
    private String fnames;

    @Bean
    public ServletRegistrationBean servletRegistrationBean()throws ServletException{
        ServletRegistrationBean servlet = new ServletRegistrationBean(new KaptchaServlet(),"/Kaptcha");
        servlet.addInitParameter("kaptcha.border",border);//无边框
        servlet.addInitParameter("kaptcha.textproducer.font.color",fcolor);
        servlet.addInitParameter("kaptcha.image.width",width);
        servlet.addInitParameter("kaptcha.textproducer.char.string",cString);
        servlet.addInitParameter("kaptcha.image.height",height);
        servlet.addInitParameter("kaptcha.textproducer.font.size",fsize);
        servlet.addInitParameter("kaptcha.noise.color",nColor);
        servlet.addInitParameter("kaptcha.textproducer.char.length",clength);
        servlet.addInitParameter("kaptcha.textproducer.font.names",fnames);
        return  servlet;
    }

    //拦截器配置

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String interceptPath = "/shopadmin/**";
        //注册拦截器
        InterceptorRegistration loginIR = registry.addInterceptor(new ShopLoginInterceptor());
        //配置拦截路径
        loginIR.addPathPatterns(interceptPath);
        //注册另一个拦截器
        InterceptorRegistration permissIR = registry.addInterceptor(new ShopPermissionInterceptor());
        //配置拦截路径
        permissIR.addPathPatterns(interceptPath);
        //设置不拦截的路由
        //shoplist page
        permissIR.excludePathPatterns("/shopadmin/shoplist");
        permissIR.excludePathPatterns("/shopadmin/getshoplist");
        //shopregister page
        permissIR.excludePathPatterns("/shopadmin/getshopinitinfo");
        permissIR.excludePathPatterns("/shopadmin/registershop");
        permissIR.excludePathPatterns("/shopadmin/shopoperation");
        //shopmanage page
        permissIR.excludePathPatterns("/shopadmin/shopmanage");
        permissIR.excludePathPatterns("/shopadmin/getshopmanagementinfo");
        //shopauthmanagement page
        permissIR.excludePathPatterns("/shopadmin/addshopauthmap");
        permissIR.excludePathPatterns("/shopadmin/operationsuccess");
        permissIR.excludePathPatterns("/shopadmin/operationfail");
    }


}

package com.imooc.mall.config;

import com.imooc.mall.filter.AdminFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: AdminFilterConfig
 * Package: com.imooc.mall.config
 *
 * @Author 马学兴
 * @Create 2023/6/2 15:40
 * @Version 1.0
 * Description:Admin过滤器的配置
 */
@Configuration
public class AdminFilterConfig {
    @Bean
    public AdminFilter adminFilter(){
        return new AdminFilter();
    }
    @Bean(name = "adminFilterConf")
    public FilterRegistrationBean adminFilterConfig(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(adminFilter());
        filterRegistrationBean.addUrlPatterns("/admin/category/*");
        filterRegistrationBean.addUrlPatterns("/admin/product/*");
        filterRegistrationBean.addUrlPatterns("/admin/order/*");
        filterRegistrationBean.setName("adminFilterConf");
        return filterRegistrationBean;
    }
}

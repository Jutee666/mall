package com.imooc.mall.config;

import com.imooc.mall.filter.AdminFilter;
import com.imooc.mall.filter.UserFilter;
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
 * Description:User过滤器的配置
 */
@Configuration
public class UserFilterConfig {
    @Bean
    public UserFilter userFilter(){
        return new UserFilter();
    }
    @Bean(name = "userFilterConf")
    public FilterRegistrationBean adminFilterConfig(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(userFilter());
        filterRegistrationBean.addUrlPatterns("/cart/*");
        filterRegistrationBean.addUrlPatterns("/order/*");
        filterRegistrationBean.setName("userFilterConf");
        return filterRegistrationBean;
    }
}

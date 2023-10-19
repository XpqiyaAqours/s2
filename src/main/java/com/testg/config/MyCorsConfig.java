package com.testg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
//跨域处理
@Configuration
public class MyCorsConfig {
    @Bean
    public CorsFilter corsFilters(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:8888");//允许跨域的前端服务器
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedMethod("*");//允许的请求方法，默认全选
        corsConfiguration.addAllowedHeader("*");//允许的头信息
        //设置映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);


    }
}

package kr.inhatc.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
    
    @Value(value = "${ImgLocation}")
    private String imgLocation;
    
    @Value(value = "${resourceHandler}")
    private String resourceHandler;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //리소스 핸들러는 images에 실제 리소스 위치는 uploadPath
        registry.addResourceHandler(resourceHandler).addResourceLocations(imgLocation);
    }
}

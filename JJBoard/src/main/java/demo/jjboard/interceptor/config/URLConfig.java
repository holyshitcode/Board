package demo.jjboard.interceptor.config;


import demo.jjboard.interceptor.URLInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class URLConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new URLInterceptor())
                .excludePathPatterns("/login", "/css/**", "/js/**", "/images/**","/member","/member/**"); // 로그인 페이지 및 정적 리소스는 제외
    }
}

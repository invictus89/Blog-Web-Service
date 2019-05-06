package edu.autocar.start.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.UrlFilenameViewController;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import edu.autocar.interceptor.AdminInterceptor;
import edu.autocar.interceptor.LoginInterceptor;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = { "edu.autocar" })
public class MvcConfig implements WebMvcConfigurer {
//	@Override
//	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//		configurer.enable();
//	}
//	

	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("/resources/")
				.setCachePeriod(0);
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.viewResolver(new BeanNameViewResolver());
		
		TilesViewResolver viewResolver = new TilesViewResolver();
		registry.viewResolver(viewResolver);
		registry.jsp("/WEB-INF/views/", ".jsp"); // prefix, surfix
	}

	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions(new String[] { "/WEB-INF/tiles/tiles-layout.xml" });
		tilesConfigurer.setCheckRefresh(true);
		return tilesConfigurer;
	}

	@Bean
	public LoginInterceptor loginInterceptor() {
		return new LoginInterceptor();
	}

	@Bean
	public AdminInterceptor adminInterceptor() {
		return new AdminInterceptor();
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("utf-8");
		return resolver;
	}
	
	
	
	@Bean
	UrlFilenameViewController urlViewController() {
		return new UrlFilenameViewController();
	}
	
	@Bean
	public SimpleUrlHandlerMapping sampleServletMapping() {
	    SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
	    mapping.setOrder(Integer.MAX_VALUE - 2);

//	    Properties urlProperties = new Properties();
//	    urlProperties.put("/map/*", "urlViewController");

	    Map<String, Object> urlMap = new HashMap<>();
        urlMap.put("/map/**", urlViewController());
        
        mapping.setUrlMap(urlMap);
        mapping.setAlwaysUseFullPath(true);
      
        
	    // mapping.setMappings(urlProperties);
	    return mapping;
	}

	@Autowired
	AdminInterceptor adminInterceptor;
	
	@Autowired
	LoginInterceptor loginInterceptor;
	
	// 인터셉터 등록
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(loginInterceptor)
    			.addPathPatterns(new String[] {
			    					"/member/**", 
			    					"/admin/**", 
			    					"/board/*"})
         		.excludePathPatterns(new String[] {
         							"/member/avata/*",
		         					"/board/list",
		         					"/board/view"});

    	registry.addInterceptor(adminInterceptor)
				.addPathPatterns("/admin/**");

	}
}

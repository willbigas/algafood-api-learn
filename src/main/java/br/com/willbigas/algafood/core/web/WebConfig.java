package br.com.willbigas.algafood.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;


/**
 *  Configura CORS para o projeto globalmente
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ApiDeprecationHandler apiDeprecationHandler;
    private final ApiRetirementHandler apiRetirementHandler;

    public WebConfig(ApiDeprecationHandler apiDeprecationHandler, ApiRetirementHandler apiRetirementHandler) {
        this.apiDeprecationHandler = apiDeprecationHandler;
        this.apiRetirementHandler = apiRetirementHandler;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("*");
    }

    /**
     * Habilita Filtro de ETag para retornar no response das requisições pro browser criar o tratamento de cache
     * @return
     */
    @Bean
    public Filter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }


    /**
     * Adicionado interceptador para comunicar a depreciação da API via Header da Response
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiDeprecationHandler);
//        registry.addInterceptor(apiRetirementHandler);
    }

}

package br.com.willbigas.algafood.core.log;

import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * Definir Log padrão do spring para requests.
 */
//@Configuration
public class RequestLoggingFilterConfig {

//    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setIncludeClientInfo(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(true);
        filter.setAfterMessagePrefix("DADOS DA REQUISIÇÃO : ");

        return filter;
    }
}

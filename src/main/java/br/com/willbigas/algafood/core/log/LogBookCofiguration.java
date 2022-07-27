package br.com.willbigas.algafood.core.log;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Logbook;

import static com.google.common.collect.Sets.newHashSet;
import static org.zalando.logbook.Conditions.*;

@Configuration
public class LogBookCofiguration {

    @Bean
    public Logbook logbook() {
        return Logbook.builder()
                .condition(exclude(
                        requestTo("/health"),
                        requestTo("/admin/**"),
                        contentType("application/octet-stream"),
                        header("X-Secret", newHashSet("1", "true")::contains)))
                .build();
    }
}

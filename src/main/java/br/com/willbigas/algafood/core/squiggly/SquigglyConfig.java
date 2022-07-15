package br.com.willbigas.algafood.core.squiggly;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SquigglyConfig {


    //localhost:8080/pedidos/?campos=codigo,segundoAtributo,TerceiroAtributo
    // Possivel tambem pesquisar por Objeto.Atributo ex:  localhost:8080/pedidos/?campos=restaurante.nome
    // localhost:8080/restaurantes/?campos=id,nome,ativo,cozinha[id,nome],endereco[cep,numero,bairro]
    // possivel negar atributos dentro do objeto tambem localhost:8080/restaurantes/?campos=id,nome,ativo,cozinha[-nome],endereco[-cidade]

    @Bean
    public FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilterFilterRegistrationBean(ObjectMapper objectMapper) {
        Squiggly.init(objectMapper, new RequestSquigglyContextProvider("campos" , null));

        List<String> urlPatterns = Arrays.asList("/pedidos/*", "/restaurantes/*");// somente habilitar se quiser limitar em endpoints especificos.

        FilterRegistrationBean<SquigglyRequestFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new SquigglyRequestFilter());
        filterRegistration.setOrder(1);
        filterRegistration.setUrlPatterns(urlPatterns);

        return filterRegistration;
    }
}

package br.com.willbigas.algafood.core.jackson;

import br.com.willbigas.algafood.api.model.mixin.RestauranteMixin;
import br.com.willbigas.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule(){
        setMixInAnnotation(Restaurante.class , RestauranteMixin.class);
    }

}

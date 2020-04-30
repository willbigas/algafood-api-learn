package br.com.willbigas.algafood.annotation;


import br.com.willbigas.algafood.di.notificacao.enums.NivelUrgencia;
import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface TipoDoNotificador {

    NivelUrgencia value();
}

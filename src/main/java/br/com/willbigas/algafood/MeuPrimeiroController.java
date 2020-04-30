package br.com.willbigas.algafood;


import br.com.willbigas.algafood.di.modelo.Cliente;
import br.com.willbigas.algafood.di.service.AtivacaoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MeuPrimeiroController {

    @Autowired
    private AtivacaoClienteService ativacaoClienteService;


    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        Cliente joao = new Cliente("João", "João@xyz.com", "34989996824");
        ativacaoClienteService.ativar(joao);

        return "Hello!";
    }
}

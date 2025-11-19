package br.com.guilhelp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/privado")
public class PrivadoController {
    @GetMapping
    public String privado() {
        return "Conteúdo Privado Acessado com Sucesso!";
    }
}

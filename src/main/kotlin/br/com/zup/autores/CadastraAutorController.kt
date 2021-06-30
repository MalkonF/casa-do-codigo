package br.com.zup.autores

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastraAutorController {
    @Post//vc pode omitir o @Body que o micronaut injeta o corpo da requisição no NovoAutorRequest mesmo assim
    fun cadastra(@Body @Valid request: NovoAutorRequest) {//@Valid diz ao micronaut que é pra validar o NovoAutorRequest
        print(request)
    }
}
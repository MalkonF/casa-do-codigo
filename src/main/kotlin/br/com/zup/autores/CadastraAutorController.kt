package br.com.zup.autores

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post

@Controller("/autores")
class CadastraAutorController {

    @Post//vc pode omitir o @Body que o micronaut injeta o corpo da requisição no NovoAutorRequest mesmo assim
    fun cadastra(@Body request: NovoAutorRequest) {
        print(request)
    }
}
package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated

import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastraAutorController(val autorRepository: AutorRepository) {
    @Post//vc pode omitir o @Body que o micronaut injeta o corpo da requisição no NovoAutorRequest mesmo assim
    fun cadastra(@Body @Valid request: NovoAutorRequest): HttpResponse<Any> {//@Valid diz ao micronaut que é pra validar o NovoAutorRequest
        println("Requisição => ${request}")
        val autor = request.paraAutor()
        autorRepository.save(autor)
        println("Autor => ${autor.nome}")
        val uri = UriBuilder.of("/autores/{id}").expand(mutableMapOf(Pair("id", autor.id)))
        return HttpResponse.created(uri)
    }
}
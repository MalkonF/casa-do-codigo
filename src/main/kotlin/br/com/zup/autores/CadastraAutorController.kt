package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.transaction.Transactional

import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastraAutorController(
    val autorRepository: AutorRepository,
    val enderecoClient: EnderecoClient
) {
    @Post//vc pode omitir o @Body que o micronaut injeta o corpo da requisição no NovoAutorRequest mesmo assim
    @Transactional
    fun cadastra(@Body @Valid request: NovoAutorRequest): HttpResponse<Any> {//@Valid diz ao micronaut que é pra validar o NovoAutorRequest
        println("Requisição => ${request}")
        val enderecoResponse = enderecoClient.consulta(request.cep)

        val autor = request.paraAutor(enderecoResponse.body()!!)//body permite valores nulos entao
        //vc tem que usar o !! para permitir esses valores nulos. O melhor forma seria tratar esses valores nulos
        autorRepository.save(autor)
        println("Autor => ${autor.nome}")//no pair o valor de autor.id é atribuído a id
        val uri = UriBuilder.of("/autores/{id}").expand(mutableMapOf(Pair("id", autor.id)))
        //expand vai substituir o {id} pelo conteúdo de Pair...
        return HttpResponse.created(uri)
    }
}
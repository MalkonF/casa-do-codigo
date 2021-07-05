package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import javax.transaction.Transactional


@Controller("/autores")
class BuscaAutoresController(val autorRepository: AutorRepository) {
    @Get
    @Transactional
    //defaultValue é caso não passar nenhum ?email= aí o micronaut vai tratar isso no código
    fun lista(@QueryValue(defaultValue = "") email: String): HttpResponse<Any> {
        if (email.isBlank()) {
            val autores = autorRepository.findAll()
            val resposta = autores.map { autor ->
                DetalhesDoAutorResponse(autor)
            }
            return HttpResponse.ok(resposta)
        }

        //val possivelAutor = autorRepository.findByEmail(email)
        val possivelAutor = autorRepository.buscaPorEmail(email)
        if (possivelAutor.isEmpty) {
            return HttpResponse.notFound()
        }

        val autor = possivelAutor.get()
        return HttpResponse.ok(DetalhesDoAutorResponse(autor))
    }
}
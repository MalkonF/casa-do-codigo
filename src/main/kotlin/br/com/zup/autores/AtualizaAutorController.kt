package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Put

@Controller("/autores/{id}")
class AtualizaAutorController(val autorRepository: AutorRepository) {
    @Put // mesmo quando nao coloca @body o micronaut faz o bind entre o json enviado e os atributos de atualiza
    //aqui ao inves de descricao poderiamos criar uma classe com um atributo descricao como no Java
    fun atualiza(@PathVariable id: Long, descricao: String): HttpResponse<Any> {
        val possivelAutor = autorRepository.findById(id)
        if (possivelAutor.isEmpty) {
            return HttpResponse.notFound()
        }

        val autor = possivelAutor.get()
        autor.descricao = descricao
        autorRepository.update(autor)
        return HttpResponse.ok(DetalhesDoAutorResponse(autor))
    }
}
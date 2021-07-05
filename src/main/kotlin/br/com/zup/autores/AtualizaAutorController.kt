package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Put
import javax.transaction.Transactional

@Controller("/autores/{id}")
class AtualizaAutorController(val autorRepository: AutorRepository) {
    @Put // mesmo quando nao coloca @body o micronaut faz o bind entre o json enviado e os atributos de atualiza
    //aqui ao inves de descricao poderiamos criar uma classe com um atributo descricao como no Java
    @Transactional
    fun atualiza(@PathVariable id: Long, descricao: String): HttpResponse<Any> {
        val possivelAutor = autorRepository.findById(id)
        if (possivelAutor.isEmpty) {
            return HttpResponse.notFound()
        }

        val autor = possivelAutor.get()
        autor.descricao = descricao
        //autorRepository.update(autor) //como foi add o @Transacional, nao precisa mais o metodo update
        //pois quando se busca o autor no bd ele fica em estado MANAGED, ou seja, pode ser feita alterações
        // aí assim que alteramos a descrição quando fecharmos a conexão com bd ele automaticamente vai
        //ser atualizado
        return HttpResponse.ok(DetalhesDoAutorResponse(autor))
    }
}
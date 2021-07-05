package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Client("http://localhost:8081/cep/busca")
interface EnderecoClient {
    @Get(consumes = [MediaType.APPLICATION_XML])//indica que vai consumir formato xml, a api do outro lado vai retornar xml
    //o método abaixo vai montar essa requisição: http://localhost:8081/cep/busca?cep=XXXXX-XX
    //@Consumes(MediaType.APPLICATION_XML) //outra forma de usar, mesmo resultado da de cima
    //@Produces(MediaType.APPLICATION_XML) //adequado p o metodo POST, vai produzir em xml
    fun consulta(@QueryValue cep: String): HttpResponse<EnderecoResponse>

}

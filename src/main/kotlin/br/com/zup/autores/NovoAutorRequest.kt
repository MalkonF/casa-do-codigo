package br.com.zup.autores

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

//data class cria as properties e hashCode, equals, toString
@Introspected //indica que no momento da compilação vai produzir um BeanIntrospection, isso vai
// permitir os atributos serem acessados para fazer a validação
data class NovoAutorRequest(
    @field:NotBlank val nome: String,
    @field:NotBlank @field:Email val email: String,
    @field:NotBlank @field: Size(max = 400) val descricao: String
) {
    fun paraAutor(): Autor {
        return Autor(nome, email, descricao)
    }
}



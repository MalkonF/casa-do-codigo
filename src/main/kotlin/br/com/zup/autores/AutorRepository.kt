package br.com.zup.autores

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository//no momento da compilação vai gerar o código dessa anotação para fazer operaçoes no banco
interface AutorRepository : JpaRepository<Autor, Long> {
    fun findByEmail(email: String): Optional<Autor>
}

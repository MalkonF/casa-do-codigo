package br.com.zup.autores

import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
internal class BuscaAutoresControllerTest {
    @field: Inject
    lateinit var autorRepository: AutorRepository
    @field: Inject
    @field: Client("/")
    lateinit var client: HttpClient
    lateinit var autor: Autor

    @BeforeEach
    internal fun setUp() {
        val enderecoResponse = EnderecoResponse("Rua Laranjeiras", "Goiânia", "GO")
        val endereco = Endereco(enderecoResponse, "74000-000", "28")
        autor = Autor("Malkon Faria", "malkon.inf@gmail.com", "Programador", endereco)
        autorRepository.save(autor)
    }

    @AfterEach
    internal fun tearDown() {
        autorRepository.deleteAll()
    }

    @Test
    internal fun `deve retornar os detalhes de um autor`() {
        //por padrão o client do micronaut é reativo, para ter o client blocante
        //que vai executar na mesma thread da execução
        val resposta =
            client.toBlocking().exchange("/autores?email=${autor.email}", DetalhesDoAutorResponse::class.java)
        //vai serializar o json para dentro da classe DetalhesDoAutorResponse
        assertEquals(HttpStatus.OK, resposta.status)
        assertNotNull(resposta.body())
        assertEquals(autor.nome, resposta.body()!!.nome)
        assertEquals(autor.email, resposta.body()!!.email)
        assertEquals(autor.descricao, resposta.body()!!.descricao)
    }
}
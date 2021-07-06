package br.com.zup.autores

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import javax.inject.Inject

@MicronautTest//sobe o contexto do Micronaut, assim poderemos utilizar os beans tipo @Test etc
internal class CadastraAutorControllerTest {
    @field:Inject//injetamos o EnderecoClient e mockamos ele. Lá em baixo tem uma função endereMock
    //que vai configurar o mock para EnderecoClient, ou seja, para n utlizar o objeto real
    lateinit var enderecoClient: EnderecoClient

    @field:Inject
    @field:Client("/")//lateinit diz que vai ser inicializado no futuro a var
    lateinit var client: HttpClient //vai usar para fazer a requisição para o endpoint

    @Test
    internal fun `deve cadastrar um novo autor`() {
        //cria cenário do teste
        val novoAutorRequest = NovoAutorRequest("Malkon", "malkon.inf@gmail.com", "Dev", "74000-000", "77")
        val enderecoResponse = EnderecoResponse("Rua Das Laranjeiras", "Goiânia", "GO")
        // quando alguem chamar o consulta passando o cep entao retorne o enderecoResponse que foi criado acima
        Mockito.`when`(enderecoClient.consulta(novoAutorRequest.cep)).thenReturn(HttpResponse.ok(enderecoResponse))

        //etapa de execução do método de teste
        //monta requisição POST p o endpoint /autores passando o novoAutorRequest que foi criado acima
        val request = HttpRequest.POST("/autores", novoAutorRequest)
        // client faz executar a requisição post montado acima
        val response = client.toBlocking().exchange(request, Any::class.java)

        // validações
        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location")!!.matches("/autores/\\d".toRegex()))
    }

    @MockBean(EnderecoClient::class)
    fun enderecoMock(): EnderecoClient {
        return Mockito.mock(EnderecoClient::class.java)
    }
}
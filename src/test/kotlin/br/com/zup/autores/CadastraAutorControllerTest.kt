package br.com.zup.autores

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import javax.inject.Inject

//rollback = false
//TransactionMode = TransactinMode.SEPARATE_TRANSACTIONS or SINGLE_TRANSACTION
//transactional = false
@MicronautTest()//sobe o contexto do Micronaut, assim poderemos utilizar os beans tipo @Test etc
internal class CadastraAutorControllerTest {
    @Inject
    lateinit var repository: AutorRepository

    @field:Inject//injetamos o EnderecoClient e mockamos ele. Lá em baixo tem uma função endereMock
    //que vai configurar o mock para EnderecoClient, ou seja, para n utlizar o objeto real
    lateinit var enderecoClient: EnderecoClient

    @field:Inject
    @field:Client("/")//lateinit diz que vai ser inicializado no futuro a var
    lateinit var client: HttpClient //vai usar para fazer a requisição para o endpoint

    @BeforeEach
    fun setUp() {
        repository.deleteAll()
    }

    @AfterEach
    fun cleanUp() {
        repository.deleteAll()
    }

    @Test
    internal fun `deve cadastrar um novo autor`() {
        //cria cenário do teste
        val novoAutorRequest = NovoAutorRequest("Malffkon", "malkon.inf@gmail.com", "Dev", "74000-000", "77")
        val enderecoResponse = EnderecoResponse("Rua Das Laranjeiras", "Goiânia", "GO")
        val endereco = Endereco(enderecoResponse, "87", "74000-000")

        // quando alguem chamar o consulta passando o cep entao retorne o enderecoResponse que foi criado acima
        Mockito.`when`(enderecoClient.consulta(novoAutorRequest.cep)).thenReturn(HttpResponse.ok(enderecoResponse))

        //etapa de execução do método de teste
        //monta requisição POST p o endpoint /autores passando o novoAutorRequest que foi criado acima
        val request = HttpRequest.POST("/autores", novoAutorRequest)
        // client faz executar a requisição post montado acima
        val response = client.toBlocking().exchange(request, Any::class.java)

        repository.save(Autor("Malkon", "malkon.inf@gmail.com", "Dev", endereco))

        // validações
        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertEquals(1, repository.count())//aqui ele conta as entrada no banco, mas quando chega no final deste
        //teste ele dá o rollback e nada é salvo
        assertTrue(response.header("Location")!!.matches("/autores/\\d".toRegex()))
    }

    @MockBean(EnderecoClient::class)
    fun enderecoMock(): EnderecoClient {
        return Mockito.mock(EnderecoClient::class.java)
    }
}
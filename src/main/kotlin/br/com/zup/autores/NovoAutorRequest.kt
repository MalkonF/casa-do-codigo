package br.com.zup.autores

//data class cria as properties e hashCode, equals, toString
data class NovoAutorRequest(
    val nome: String,
    val email: String,
    val descricao: String
)



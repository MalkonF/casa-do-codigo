package br.com.zup.autores

import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint

//micronaut criou seus proprios modos de fazer a validação customizada pq no spring eles usam o reflection
@MustBeDocumented //ao gerar o javadoc essa classe vai ser documentada
@Target(AnnotationTarget.FIELD, AnnotationTarget.CONSTRUCTOR)//onde a anotação vai ser usada
@Retention(AnnotationRetention.RUNTIME)//anotação vai ser avaliada em tempo de execução
@Constraint(validatedBy = [CepValidator::class])
annotation class Cep(val message: String = "Cep no formato inválido")

@Singleton //p esse objeto ser conhecido no Micronaut
class CepValidator : ConstraintValidator<Cep, String> {
    override fun isValid(
        value: String?, //valor que vem do campo que foi anotado
        annotationMetadata: AnnotationValue<Cep>,
        context: ConstraintValidatorContext
    ): Boolean {

        if (value == null) {//se for nulo é valido pq se quisesse evitar que o campo n seja nulo usa-se @NotNull
            return true
        }
        return value.matches("^\\d{5}-\\d{3}\$".toRegex())
    }

}
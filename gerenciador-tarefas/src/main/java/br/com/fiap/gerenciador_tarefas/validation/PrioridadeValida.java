package br.com.fiap.gerenciador_tarefas.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.util.List;

@Documented
@Constraint(validatedBy = PrioridadeValida.PrioridadeValidaValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PrioridadeValida {

    String message() default "Prioridade inválida. Use: BAIXA, MEDIA ou ALTA";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class PrioridadeValidaValidator implements ConstraintValidator<PrioridadeValida, String> {

        private static final List<String> VALORES = List.of("BAIXA", "MEDIA", "ALTA");

        @Override
        public boolean isValid(String valor, ConstraintValidatorContext ctx) {
            if (valor == null) return false;
            return VALORES.contains(valor.toUpperCase());
        }
    }
}
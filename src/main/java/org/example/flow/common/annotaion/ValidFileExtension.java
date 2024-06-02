package org.example.flow.common.annotaion;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.flow.common.annotaion.validator.FileExtensionValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.PARAMETER, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = FileExtensionValidator.class)
public @interface ValidFileExtension {

    String message() default "This file extension is not allowed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
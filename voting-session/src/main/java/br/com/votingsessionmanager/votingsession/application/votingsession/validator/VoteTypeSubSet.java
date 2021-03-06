package br.com.votingsessionmanager.votingsession.application.votingsession.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.com.votingsessionmanager.votingsession.domain.agenda.VoteType;

/**
 * The class {@code VoteTypeSubSet} is used as validation the field
 * that will be receive a {@code VoteType} Enum value
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = VoteTypeSubSetValidator.class)
public @interface VoteTypeSubSet {
	VoteType[] anyOf();

	String message() default "must be any of {anyOf}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

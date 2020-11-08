package br.com.votingsessionmanager.votingsession.application.votingsession.validator;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.votingsessionmanager.votingsession.domain.agenda.VoteType;

/**
 * The class {@code VoteTypeSubSetValidator} is used to validate a {@code VoteType} Enum
 */
public class VoteTypeSubSetValidator implements ConstraintValidator<VoteTypeSubSet, VoteType> {
	private VoteType[] subset;

	@Override
	public void initialize(VoteTypeSubSet constraint) {
		this.subset = constraint.anyOf();
	}

	@Override
	public boolean isValid(VoteType value, ConstraintValidatorContext context) {
		return value != null || Arrays.asList(subset).contains(value);
	}
}
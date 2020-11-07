package br.com.votingsessionmanager.votingsessionmanager.application.votingsession.validator;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.votingsessionmanager.votingsessionmanager.domain.agenda.VoteType;

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
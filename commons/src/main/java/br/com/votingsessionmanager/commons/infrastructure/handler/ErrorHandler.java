package br.com.votingsessionmanager.commons.infrastructure.handler;

import java.text.MessageFormat;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import br.com.votingsessionmanager.commons.application.exception.InvalidResourceException;
import br.com.votingsessionmanager.commons.application.exception.InvalidResourceForeignKeyReferenceOnResourceException;
import br.com.votingsessionmanager.commons.application.exception.InvalidResourceReferenceException;
import br.com.votingsessionmanager.commons.application.exception.associate.AssociateUnableToVoteException;
import br.com.votingsessionmanager.commons.application.exception.sessionvote.VotingSessionAlreadyOpenedWithAgendaException;
import br.com.votingsessionmanager.commons.application.exception.sessionvote.VotingSessionAlreadyReceiveVoteFromAssociate;
import br.com.votingsessionmanager.commons.application.exception.sessionvote.VotingSessionAreNotAbleToReceiveVotesOnAgenda;

@RestControllerAdvice
public class ErrorHandler {

	private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorCustom methodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
		Set<FieldErrorCustom> errorsCustom = ex.getBindingResult().getFieldErrors()
				.stream().map(FieldErrorCustom::new)
				.collect(Collectors.toSet());

		ErrorCustom error = new ErrorCustom(errorsCustom);
		logErrorMessages(error);
		return error;
	}

	@ExceptionHandler(InvalidResourceException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void invalidResourceException(InvalidResourceException ex) {
		logger.error("Unable to get resource {} with id {} ", parse(ex.getResource()), parse(ex.getId()));
	}

	@ExceptionHandler(InvalidResourceReferenceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorCustom invalidResourceRefereceException(InvalidResourceReferenceException ex) {
		String message = MessageFormat.format("with value {0} does not exists", parse(ex.getId()));
		ErrorCustom error = buildErrorCustom(new FieldError("agenda_id", ex.getResource(), message));
		logErrorMessages(error);
		return error;
	}

	@ExceptionHandler(InvalidResourceForeignKeyReferenceOnResourceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorCustom invalidAgendaResourceReferenceOnVotingSessionException(InvalidResourceForeignKeyReferenceOnResourceException ex) {
		String message = MessageFormat.format("with value {0} is not present on {1} resource", parse(ex.getForeignKeyResourceId()), ex.getMainResource());
		ErrorCustom error = buildErrorCustom(new FieldError("resource_id", parse(ex.getForeignResource()),message));
		logErrorMessages(error);
		return error;
	}

	@ExceptionHandler(VotingSessionAlreadyOpenedWithAgendaException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorCustom votingSessionAlreadyOpenedWithAgendaException(VotingSessionAlreadyOpenedWithAgendaException ex) {
		String message = MessageFormat.format("{0} already opened", parse(ex.getAgendaId()));
		ErrorCustom error = buildErrorCustom(new FieldError("agenda", "agenda_id", message));
		logErrorMessages(error);
		return error;
	}

	@ExceptionHandler(VotingSessionAreNotAbleToReceiveVotesOnAgenda.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorCustom votingSessionAreNotAbleToReceiveVotesOnAgenda(VotingSessionAreNotAbleToReceiveVotesOnAgenda ex) {
		String message = MessageFormat.format("with value {0} is not able to accept more votes because voting_session has been closed", parse(ex.getAgendaId()));
		ErrorCustom error = buildErrorCustom(new FieldError("agenda", "agenda_id", message));
		logErrorMessages(error);
		return error;
	}

	@ExceptionHandler(VotingSessionAlreadyReceiveVoteFromAssociate.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorCustom votingSessionAlreadyReceiveVoteFromAssociate(VotingSessionAlreadyReceiveVoteFromAssociate ex) {
		String message = MessageFormat.format("with value {0} already vote on this agenda", parse(ex.getAssociateId()));
		ErrorCustom error = buildErrorCustom(new FieldError("associate", "associate_id", message));
		logErrorMessages(error);
		return error;
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorCustom dataIntegrityViolationException(DataIntegrityViolationException ex) {
		String message = "cant be processed, check it and try again";
		ErrorCustom error = buildErrorCustom(new FieldError("resource", "resource_id", message));
		logErrorMessages(error);
		return error;
	}

	@ExceptionHandler(AssociateUnableToVoteException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorCustom associateUnableToVoteException(AssociateUnableToVoteException ex) {
		String message = MessageFormat.format("with value {0} is unable to vote on this agenda", parse(ex.getAssociateId()));
		ErrorCustom error = buildErrorCustom(new FieldError("associate", "associate_id", message));
		logErrorMessages(error);
		return error;
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public void internalServerError(Exception ex) {
		logger.error("Internal Server Error", ex);
	}
	
	private ErrorCustom buildErrorCustom(FieldError... fieldErrors) {
		Set<FieldErrorCustom> errorsCustom = Stream.of(fieldErrors).map(FieldErrorCustom::new).collect(Collectors.toSet());
		ErrorCustom error = new ErrorCustom(errorsCustom);
		return error;
	}

	private void logErrorMessages(ErrorCustom errorCustom) {
		errorCustom.getErrorMessages().stream().forEach(errorMessage -> logger.error(errorMessage));
	}

	private <T> String parse(T identifier) {
		return "" + identifier;
	}

}

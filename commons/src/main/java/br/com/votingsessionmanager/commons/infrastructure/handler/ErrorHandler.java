package br.com.votingsessionmanager.commons.infrastructure.handler;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import br.com.votingsessionmanager.commons.application.exception.sessionvote.VotingSessionAlreadyOpenedWithAgendaException;
import br.com.votingsessionmanager.commons.application.exception.sessionvote.VotingSessionAlreadyReceiveVoteFromAssociate;
import br.com.votingsessionmanager.commons.application.exception.sessionvote.VotingSessionAreNotAbleToReceiveVotesOnAgenda;

@RestControllerAdvice
public class ErrorHandler {

	private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
	public ErrorCustom methodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
		Set<FieldErrorCustom> errorsCustom = ex.getBindingResult().getFieldErrors()
				.stream().map(FieldErrorCustom::new)
				.collect(Collectors.toSet());

		ErrorCustom error = new ErrorCustom(errorsCustom);
		return error;

	}

	@ExceptionHandler(value = InvalidResourceException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public void invalidResourceException(InvalidResourceException ex) {
		logger.warn("Unable to get resource {} with id {} ", ex.getResource(), ex.getId());
	}

	@ExceptionHandler(value = InvalidResourceReferenceException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorCustom invalidResourceRefereceException(InvalidResourceReferenceException ex) {
		String message = MessageFormat.format("with value {0} does not exists", ex.getId());
		logger.warn(message);

		ErrorCustom error = buildErrorCustom(new FieldError("agenda_id", ex.getResource(), message));
		return error;
	}

	@ExceptionHandler(value = InvalidResourceForeignKeyReferenceOnResourceException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorCustom invalidAgendaResourceReferenceOnVotingSessionException(InvalidResourceForeignKeyReferenceOnResourceException ex) {
		String message = MessageFormat.format("with value {0} is not present on {1} resource", ex.getForeignKeyResourceId(), ex.getMainResource());
		logger.warn(message);
		ErrorCustom error = buildErrorCustom(new FieldError("resource_id", ex.getForeignResource(),message));
		return error;
	}
	
	@ExceptionHandler(value = VotingSessionAlreadyOpenedWithAgendaException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorCustom votingSessionAlreadyOpenedWithAgendaException(VotingSessionAlreadyOpenedWithAgendaException ex) {
		String message = MessageFormat.format("{0} already is opened", ex.getAgendaId());
		logger.warn(message);
		
		HashSet<FieldErrorCustom> errorsCustom = new HashSet<>();
		errorsCustom.add(new FieldErrorCustom(new FieldError("agenda", "agenda_id", message)));
		
		ErrorCustom error = new ErrorCustom(errorsCustom);
		return error;
	}

	@ExceptionHandler(value = VotingSessionAreNotAbleToReceiveVotesOnAgenda.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorCustom votingSessionAreNotAbleToReceiveVotesOnAgenda(VotingSessionAreNotAbleToReceiveVotesOnAgenda ex) {
		String message = MessageFormat.format("with value {0} is not able to accept more votes because voting_session has been closed", ex.getAgendaId());
		logger.warn(message);
		ErrorCustom error = buildErrorCustom(new FieldError("agenda", "agenda_id", message));
		return error;
	}

	@ExceptionHandler(value = VotingSessionAlreadyReceiveVoteFromAssociate.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorCustom votingSessionAlreadyReceiveVoteFromAssociate(VotingSessionAlreadyReceiveVoteFromAssociate ex) {
		String message = MessageFormat.format("with value {0} already vote on this agenda", ex.getAssociateId());
		logger.warn(message);

		ErrorCustom error = buildErrorCustom(new FieldError("associate", "associate_id", message));
		return error;
	}
	
	private ErrorCustom buildErrorCustom(FieldError... fieldErrors) {
		Set<FieldErrorCustom> errorsCustom = Stream.of(fieldErrors).map(FieldErrorCustom::new).collect(Collectors.toSet());
		ErrorCustom error = new ErrorCustom(errorsCustom);
		return error;
	}

}

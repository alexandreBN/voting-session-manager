package br.com.votingsessionmanager.commons.infrastructure.handler;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.google.common.base.CaseFormat;

import br.com.votingsessionmanager.commons.application.exception.InvalidResourceException;
import br.com.votingsessionmanager.commons.application.exception.InvalidResourceReferenceException;
import br.com.votingsessionmanager.commons.application.exception.sessionvote.VotingSessionAlreadyOpenedWithAgendaException;

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
	public ResponseEntity<ErrorCustom> invalidResourceRefereceException(InvalidResourceReferenceException ex) {
		logger.warn("Unable to get resource {} with id {} ", ex.getResource(), ex.getId());
		
		return null;
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
}

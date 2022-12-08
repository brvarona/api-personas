package com.brvarona.personas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Data
@EqualsAndHashCode(callSuper = false)
public class EntityAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String message;

	public EntityAlreadyExistsException(String msg) {
		super(msg);
		this.message = msg;
	}

}

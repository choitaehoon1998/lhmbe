package com.cth.lhmbe.exceptions;

import org.springframework.security.core.AuthenticationException;

public class StoreMemberMisMatchException extends AuthenticationException {

	public StoreMemberMisMatchException(String msg) {
		super(msg);
	}

	public StoreMemberMisMatchException(String msg, Throwable cause) {
		super(msg, cause);
	}

}

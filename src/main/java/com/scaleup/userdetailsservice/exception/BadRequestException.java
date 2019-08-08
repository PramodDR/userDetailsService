package com.scaleup.userdetailsservice.exception;

public class BadRequestException extends RuntimeException {
	// TODO to be enhanced
	private static final long serialVersionUID = 1L;

	public BadRequestException(final String s) {
		super(s);
	}

	public BadRequestException(final Throwable e) {
		super(e);
	}
}

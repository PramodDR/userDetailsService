package com.scaleup.userdetailsservice.exception;

public class ConflictException extends RuntimeException {
	// TODO to be enhanced
	private static final long serialVersionUID = 1L;

	public ConflictException(final String s) {
		super(s);
	}

	public ConflictException(final Throwable e) {
		super(e);
	}
}

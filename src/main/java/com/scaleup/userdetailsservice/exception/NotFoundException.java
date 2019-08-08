package com.scaleup.userdetailsservice.exception;

public class NotFoundException extends RuntimeException {
	// TODO to be enhanced
	private static final long serialVersionUID = 1L;

	public NotFoundException(final String s) {
		super(s);
	}

	public NotFoundException(final Throwable e) {
		super(e);
	}

}

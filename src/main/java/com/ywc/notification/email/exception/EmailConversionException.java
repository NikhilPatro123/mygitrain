package com.ywc.notification.email.exception;

public class EmailConversionException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4528226834013924023L;

	public EmailConversionException() {
        super();
    }

    public EmailConversionException(final String message) {
        super(message);
    }

    public EmailConversionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public EmailConversionException(final Throwable cause) {
        super(cause);
    }

}
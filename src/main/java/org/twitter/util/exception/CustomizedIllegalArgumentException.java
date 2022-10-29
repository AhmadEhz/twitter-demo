package org.twitter.util.exception;

public class CustomizedIllegalArgumentException extends IllegalArgumentException {
    public CustomizedIllegalArgumentException() {
    }

    public CustomizedIllegalArgumentException(String s) {
        super(s);
    }

    public CustomizedIllegalArgumentException(Throwable cause) {
        super(cause);
    }

    public CustomizedIllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}

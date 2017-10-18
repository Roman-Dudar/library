package org.dudar.exception;

public class DatabaseException extends RuntimeException{

    //todo-Dmitry: throw exception and handle it !important

    private static final long serialVersionUID = 101L;

    public DatabaseException(String messageKey) {
        super(messageKey);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }

    public DatabaseException(String messageKey, Throwable cause) {
        super(messageKey, cause);
    }

}

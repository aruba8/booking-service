package ca.erik.bs.dao.exception;

public class DatabaseException extends Exception {
    public DatabaseException(Throwable e) {
        e.printStackTrace();
    }
}

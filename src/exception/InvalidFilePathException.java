package exception;

public class InvalidFilePathException extends RuntimeException{
    public InvalidFilePathException(String message) {
        super(message);
    }
}

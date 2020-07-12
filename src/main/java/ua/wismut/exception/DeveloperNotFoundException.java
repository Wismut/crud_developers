package ua.wismut.exception;

public class DeveloperNotFoundException extends RuntimeException {
    public DeveloperNotFoundException(Long id) {
        super("Could not found developer with id = " + id);
    }
}

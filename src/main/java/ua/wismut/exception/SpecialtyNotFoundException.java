package ua.wismut.exception;

public class SpecialtyNotFoundException extends RuntimeException {
    public SpecialtyNotFoundException(Long id) {
        super("Could not found specialty with id = " + id);
    }
}

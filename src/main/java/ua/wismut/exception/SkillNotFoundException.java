package ua.wismut.exception;

public class SkillNotFoundException extends RuntimeException {
    public SkillNotFoundException(Long id) {
        super("Could not found skill with id = " + id);
    }
}

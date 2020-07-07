package ua.wismut.util;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import java.io.IOException;

public class ExceptionHandler {
    public static String handle(UnrecognizedPropertyException e) {
        return "Can not find field '" + e.getPropertyName() + "' in " + e.getKnownPropertyIds();
    }

    public static String handle(IOException e) {
        return "Unknown error: " + e.getMessage();
    }

    public static String handle(MismatchedInputException e) {
        return "Incorrect request body";
    }
}

package util;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import java.io.IOException;

public class ExceptionHandler {
    public static String handle(UnrecognizedPropertyException e) {
        return "Can not find field '" + e.getPropertyName() + "' in " + e.getKnownPropertyIds();
    }

    public static String handle(IOException e) {
        return "" + e.getMessage();
    }
}

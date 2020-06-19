package util;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

public class ExceptionHandler {
    public static String handle(UnrecognizedPropertyException e) {
        return "Can not find field '" + e.getPropertyName() + "' in " + e.getKnownPropertyIds();
    }
}

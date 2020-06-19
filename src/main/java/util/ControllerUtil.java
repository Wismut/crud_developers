package util;

import org.junit.platform.commons.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class ControllerUtil {
    public static String getPathVariableFrom(HttpServletRequest req) {
        String pathVariable;
        if (StringUtils.isBlank(req.getPathInfo()) ||
                !req.getPathInfo().contains("/")) {
            pathVariable = null;
        } else {
            String[] pathSplitted = req.getPathInfo().split("/");
            if (pathSplitted.length < 2) {
                pathVariable = null;
            } else {
                pathVariable = pathSplitted[1];
            }
        }
        return pathVariable;
    }
}

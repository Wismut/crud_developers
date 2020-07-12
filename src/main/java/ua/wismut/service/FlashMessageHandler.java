package ua.wismut.service;

import javax.servlet.http.HttpServletRequest;

public class FlashMessageHandler {

    public static void setMessage(HttpServletRequest request, String message) {
        request.getSession().setAttribute("message", message);
    }

    public static void setMessage(HttpServletRequest request, String[] messages) {
        request.getSession().setAttribute("message", String.join("\n", messages));
    }


    public static String getMessage(HttpServletRequest request) {
        String message = (String)request.getSession().getAttribute("message");
        request.getSession().setAttribute("message", null);
        return message;
    }
}

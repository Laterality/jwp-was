package controller;

import db.DataBase;
import model.User;
import utils.UrlEncodedParser;
import webserver.Request;
import webserver.Response;

import java.util.Map;

public class UserController {

    public static final String USER_CREATE_URL = "/user/create";
    public static final String USER_LOGIN_URL = "/user/login";
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String LOGINED_COOKIE_KEY = "logined";

    public static Response signUp(Request req) {
        Map<String, String> parsedBody = UrlEncodedParser.parse(new String(req.getBody()));
        User user = new User(parsedBody.get(USER_ID),
                parsedBody.get(PASSWORD),
                parsedBody.get(NAME),
                parsedBody.get(EMAIL));
        DataBase.addUser(user);

        return Response.ResponseBuilder.redirect("/index.html")
                .build();
    }

    public static Response login(Request req) {
        Map<String, String> parsedBody = UrlEncodedParser.parse(new String(req.getBody()));
        User found = DataBase.findUserById(parsedBody.get(USER_ID));

        String redirectUrl = "/user/login_failed.html";
        String loginedCookie = "false";

        if (verifyUser(parsedBody, found)) {
            redirectUrl = "/index.html";
            loginedCookie = "true";
        }

        return Response.ResponseBuilder.redirect(redirectUrl)
                .withCookie(LOGINED_COOKIE_KEY, loginedCookie)
                .build();
    }

    private static boolean verifyUser(Map<String, String> parsedBody, User found) {
        return found != null && found.matchPassword(parsedBody.get(PASSWORD));
    }
}

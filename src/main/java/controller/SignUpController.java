package controller;

import db.DataBase;
import model.User;
import utils.UrlEncodedParser;
import webserver.Request;
import webserver.Response;

import java.util.Map;

public class SignUpController extends AbstractController {

    private static final String USER_CREATE_URL = "/user/create";

    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    @Override
    public Response doGet(Request req) {
        throw createUnsupportedException();
    }

    @Override
    public Response doPost(Request req) {
        Map<String, String> parsedBody = UrlEncodedParser.parse(new String(req.getBody()));
        User user = new User(parsedBody.get(USER_ID),
                parsedBody.get(PASSWORD),
                parsedBody.get(NAME),
                parsedBody.get(EMAIL));
        DataBase.addUser(user);

        return Response.ResponseBuilder.redirect("/index.html")
                .build();
    }

    @Override
    public String getPath() {
        return USER_CREATE_URL;
    }
}

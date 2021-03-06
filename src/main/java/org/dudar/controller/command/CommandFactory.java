package org.dudar.controller.command;

import org.dudar.controller.command.implementation.*;
import org.dudar.controller.command.implementation.account.*;
import org.dudar.controller.command.implementation.catalog.*;
import org.dudar.controller.command.implementation.order.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    /**
     * class generates command key from request method and uri
     * returns command by this key
     */
    private static String INDEX_PATH = ".*/main/";
    private static String REPLACEMENT = "";
    private static String DELIMITER = ":";

    private static Map<String, Command> commands = new HashMap<>();

    static {
        commands.put("GET:", new HomeCommand());
        commands.put("GET:pageNotFound", new PageNotFoundCommand());
        commands.put("GET:login", new GetLoginCommand());
        commands.put("POST:login", new PostLoginCommand());
        commands.put("GET:logout", new LogoutCommand());
        commands.put("GET:locale", new ChangeLocaleCommand());
        commands.put("GET:catalog", new GetCatalogCommand());
        commands.put("GET:order", new GetOrderCommand());
        commands.put("GET:signUp", new GetSignUpCommand());
        commands.put("POST:signUp", new PostSignUpCommand());
        commands.put("POST:order", new PostOrderCommand());
        commands.put("GET:findOrder", new GetFindOrderCommand());
        commands.put("POST:findOrder", new PostFindOrderCommand());
        commands.put("GET:confirmOrder", new GetConfirmOrderCommand());
        commands.put("POST:confirmOrder", new PostConfirmOrderCommand());
        commands.put("GET:searchBook", new GetSearchBookCommand());
        commands.put("POST:searchBook", new PostSearchBookCommand());
        commands.put("GET:addBook", new GetAddBookCommand());
        commands.put("POST:addBook", new PostAddBookCommand());
    }


    public static Command getCommand(HttpServletRequest request) {
        String key = getKeyFromRequest(request);
        Command command = commands.get(key);
        return (command != null) ? command : commands.get("GET:pageNotFound");
    }

    public static String getKeyFromRequest(HttpServletRequest request) {
        String method = request.getMethod().toUpperCase();
        String path = request.getRequestURI().replaceAll(INDEX_PATH, REPLACEMENT);
        String key = method + DELIMITER + path;
        System.out.println(key);
        return key;
    }
}
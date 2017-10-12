package org.dudar.controller.command;

import org.dudar.controller.command.implementation.*;
import org.dudar.controller.command.implementation.account.GetLoginCommand;
import org.dudar.controller.command.implementation.account.LogoutCommand;
import org.dudar.controller.command.implementation.account.PostLoginCommand;
import org.dudar.controller.command.implementation.catalog.GetCatalogCommand;

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
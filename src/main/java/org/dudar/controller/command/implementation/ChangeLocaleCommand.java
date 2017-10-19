package org.dudar.controller.command.implementation;

import org.dudar.controller.command.Command;
import org.dudar.controller.constants.LocaleEnum;
import org.dudar.controller.constants.Page;
import org.dudar.controller.constants.Parameters;
import org.dudar.utils.locale.LocaleManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class ChangeLocaleCommand implements Command{

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        setLocale(request);
        return Page.HOME;
    }

    private void setLocale(HttpServletRequest request) {
        String selectedLanguage = request.getParameter(Parameters.LANG);
        Locale chosenLocale = LocaleEnum.valueOf(selectedLanguage).getLocale();
        request.getSession().setAttribute(Parameters.LOCALE, chosenLocale);
        LocaleManager.setResourceBundleLocale(chosenLocale);
    }

}

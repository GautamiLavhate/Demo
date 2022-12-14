package com.levare.verificare.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    public static String getJustifyString(String data) {
        return "<html>\n" +
                " <head></head>\n" +
                " <body style=\"text-align:justify;color:black;\">" + data +
                "</body>\n" +
                "</html>";
    }

    public static boolean isEmailValid(String email) {

        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


}

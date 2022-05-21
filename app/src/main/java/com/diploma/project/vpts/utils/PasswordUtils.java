package com.diploma.project.vpts.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PasswordUtils {
    public static List<String> isSecure(String passwordInput, String passwordConfirmInput) {

        List<String> errorList = new ArrayList<>();

        if (!passwordInput.equals(passwordConfirmInput)) {
            errorList.add("Passwords don't match.");
        }
        if (passwordInput.length() < 8) {
            errorList.add("Invalid password, minimum length is 8 characters.");
        }
        return errorList;

    }
}

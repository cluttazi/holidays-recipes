package com.chrisluttazi.holidaysrecipes.auxiliary;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public final class Utilities {

    public static String currentUser() {
        SecurityContext secContext = SecurityContextHolder.getContext();
        Authentication secAuth = secContext.getAuthentication();
        Object secPrincipal = secAuth.getPrincipal();
        if (secPrincipal.toString().equalsIgnoreCase("anonymousUser")) {
            return "";
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = user.getUsername(); // get logged in username

        return name;
    }

}

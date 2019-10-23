package com.px.tool.infrastructure;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

    public static String extractRequestToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

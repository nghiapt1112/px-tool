package com.px.tool.infrastructure.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public class CommonUtils {

    public static String extractRequestToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public static String getPercentage(Integer i1, Integer i2) {
        String val = (((float) i1 * 100) / i2 + "");
        return val.length() > 5 ? val.substring(0, 6) : val;
    }

    public static String toString(Collection<Long> numbers) {
        StringBuilder s = new StringBuilder();
        for (Long cusReceiver : numbers) {
            s.append(cusReceiver).append(",");
        }
        return s.substring(0, s.length() - 1);
    }

}

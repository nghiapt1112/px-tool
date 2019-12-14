package com.px.tool.infrastructure.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        try {
            StringBuilder s = new StringBuilder();
            for (Long cusReceiver : numbers) {
                s.append(cusReceiver).append(",");
            }
            return s.substring(0, s.length() - 1);
        } catch (Exception e) {
            return "";
        }
    }

    public static List<Long> toCollection(String numberChain) {
        try {
            List<Long> val = new ArrayList<>();
            for (String s : numberChain.split(",")) {
                val.add(Long.valueOf(s));
            }
            return val;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public static String toString(Collection<Long> numbers, Map<Long, String> map) {
        try {
            StringBuilder s = new StringBuilder();
            for (Long el : numbers) {
                if (map.containsKey(el)) {
                    s.append(map.get(el)).append(",");
                }
            }
            return s.substring(0, s.length() - 1);
        } catch (Exception e) {
            return "";
        }
    }
}

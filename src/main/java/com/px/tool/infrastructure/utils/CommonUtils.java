package com.px.tool.infrastructure.utils;

import com.px.tool.domain.user.User;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
                if (cusReceiver != null) {
                    s.append(cusReceiver).append(",");
                }
            }
            return s.substring(0, s.length() - 1);
        } catch (Exception e) {
            return "";
        }
    }

    public static List<Long> toCollection(String numberChain) {
        if (StringUtils.isEmpty(numberChain)) {
            return null;
        }
        List<Long> val = new ArrayList<>();
        for (String s : numberChain.split(",")) {
            try {
                val.add(Long.valueOf(s));
            } catch (Exception e) {
            }
        }
        return val;

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

    public static <E> void collectionAdd(Collection<E> collection, E... list) {
        for (E e : list) {
            if (e != null) {
                collection.add(e);
            }
        }
    }

    public static String assignVal(User user, String defaultVal) {
        if (Objects.isNull(user)) {
            return defaultVal;
        }
        return user.getFullName();
    }

    public static <E extends Object> E getVal(List<E> longs, int index) {
        if (index + 1 > longs.size()) {
            return null;
        }
        return longs.get(index);
    }

    public static String limitStr(String str) {
        if (!StringUtils.isEmpty(str) && str.length() > 23) {
            return str.substring(0, 20) + "...";
        }
        return str;
    }
}

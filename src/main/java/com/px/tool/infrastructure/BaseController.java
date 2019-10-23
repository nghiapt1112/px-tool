package com.px.tool.infrastructure;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public abstract class BaseController {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @ExceptionHandler(RuntimeException.class)
    public void handleRootException(RuntimeException e) {

    }

    public Long extractUserInfo(HttpServletRequest httpServletRequest) {
        String token = RequestUtils.extractRequestToken(httpServletRequest);
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }
}

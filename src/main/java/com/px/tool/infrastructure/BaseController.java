package com.px.tool.infrastructure;

import com.px.tool.model.response.ErrorResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public abstract class BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRootException(RuntimeException e) {
        e.printStackTrace();
        logger.error("Message: {}\nCause: {}\n Stacktrace: {}", e.getMessage(), e.getCause(), e.getStackTrace());

        return ResponseEntity
                .badRequest()
                .body(ErrorResponse
                        .builder()
                        .message(e.getMessage())
                        .code("500")
                        .build());
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

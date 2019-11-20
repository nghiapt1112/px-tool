package com.px.tool.infrastructure;

import com.px.tool.infrastructure.exception.PXException;
import com.px.tool.infrastructure.model.ErrorResponse;
import com.px.tool.infrastructure.utils.RequestUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import javax.naming.SizeLimitExceededException;
import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public abstract class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Autowired
    private Environment environment;

    @ExceptionHandler(PXException.class)
    public ResponseEntity<ErrorResponse> handleRootException(PXException e) {
        e.printStackTrace();
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse
                        .builder()
                        .message(getStrVal(e.getMessage()))
                        .code("500")
                        .build());
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorResponse> sizeLimitExceeded(MultipartException e) {
        e.printStackTrace();
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse
                        .builder()
                        .message(getStrVal("fileSize.limitExceeded"))
                        .code("500")
                        .build());
    }

    private String getStrVal(String k) {
        return environment.getProperty(k, k);
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

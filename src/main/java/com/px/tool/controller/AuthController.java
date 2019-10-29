package com.px.tool.controller;

import com.px.tool.infrastructure.BaseController;
import com.px.tool.infrastructure.model.request.TokenInfo;
import com.px.tool.domain.user.User;
import com.px.tool.infrastructure.model.request.LoginRequest;
import com.px.tool.domain.user.repository.UserRepository;
import com.px.tool.domain.user.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sec")
public class AuthController extends BaseController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/login")
    public TokenInfo authenticateUser(@RequestBody LoginRequest loginRequest) {
        userRepository
                .findByEmail(loginRequest.getUserName())
                .orElseThrow(() -> new RuntimeException("User username not found"));

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authService.generateToken((User) authentication.getPrincipal());
    }

    /**
     * Re-login by using refresh token
     *
     * @return
     */
    @PostMapping("/token/refresh")
    public TokenInfo refreshToken(@RequestParam String refreshToken) {

        if (!this.authService.validateToken(refreshToken)) {
            throw new RuntimeException("Token Invalid");
        }

        Long userId = this.authService.getUserIdFromJWT(refreshToken);
        return authService.generateToken(authService.loadUserById(userId));
    }

    /**
     * Revoke/delete/remove/ token
     */
    @PostMapping("/token/revoke")
    public Boolean revokeToken(@RequestParam String token) {
        //TODO: remove token
        return true;
    }

}

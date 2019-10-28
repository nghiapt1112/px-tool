package com.px.tool.domain.user.service;

import com.px.tool.domain.user.User;
import com.px.tool.infrastructure.model.request.UserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> findUsers();

    User create(UserRequest user);

    Long delete(Long id);

    User findById(Long userId);
}

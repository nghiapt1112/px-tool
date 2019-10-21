package com.px.tool.service;

import com.px.tool.model.User;
import com.px.tool.model.request.UserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> findUsers();

    User create(UserRequest user);

    Long delete(Long id);

    User findById(Long userId);
}

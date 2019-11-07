package com.px.tool.domain.user.service;

import com.px.tool.domain.request.NoiNhan;
import com.px.tool.domain.user.User;
import com.px.tool.domain.user.UserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.LinkedList;
import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> findUsers();

    User create(UserRequest user);

    Long delete(Long id);

    User findById(Long userId);

    LinkedList<NoiNhan> findNoiNhan(Long extractUserInfo, Long requestId);
}

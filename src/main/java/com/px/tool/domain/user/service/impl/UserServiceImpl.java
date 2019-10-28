package com.px.tool.domain.user.service.impl;

import com.google.common.collect.Sets;
import com.px.tool.domain.user.Role;
import com.px.tool.domain.user.User;
import com.px.tool.infrastructure.model.request.UserRequest;
import com.px.tool.domain.user.repository.RoleRepository;
import com.px.tool.domain.user.repository.UserRepository;
import com.px.tool.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + username));
    }

    @Override
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    @Override
    public User create(UserRequest user) {
        Role role = roleRepository
                .findById(Long.valueOf(user.getLevel()))
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User entity = user.toUserEntity();
        entity.setPassword(passwordEncoder.encode(user.getPassword()));
        entity.setAuthorities(Sets.newHashSet(role));
        return userRepository.save(entity);
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        try {
            User user = userRepository
                    .findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            user.setDeleted(true);
            userRepository.save(user);
            return id;
        } catch (RuntimeException e) {
            throw new RuntimeException("Delete user failed.");
        }
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }
}

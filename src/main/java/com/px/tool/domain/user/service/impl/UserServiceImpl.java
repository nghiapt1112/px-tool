package com.px.tool.domain.user.service.impl;

import com.google.common.collect.Sets;
import com.px.tool.domain.request.NoiNhan;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.domain.user.Role;
import com.px.tool.domain.user.User;
import com.px.tool.domain.user.repository.RoleRepository;
import com.px.tool.domain.user.repository.UserRepository;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.domain.user.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RequestService requestService;

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
    @Transactional
    public User  create(UserRequest user) {
        Role role = roleRepository
                .findById(Long.valueOf(user.getLevel()))
                .orElseThrow(() -> new RuntimeException("Role not found"));

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User existed");
        }
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

    @Override
    public LinkedList<NoiNhan> findNoiNhan(Long extractUserInfo, Long requestId) {
        if (Objects.isNull(requestId)) {
            // tao kiem hong lan dau tien
            // find kiem hong (level 4a, 4b)
        } else {
            Request existedRequest = requestService.findById(requestId);
            existedRequest.getStatus();
        }
        return IntStream.rangeClosed(1,10)
                .mapToObj(el -> {
                    NoiNhan noiNhan = new NoiNhan();
                    noiNhan.setId(Long.valueOf(el));
                    noiNhan.setName("Name __" + el);
                    return noiNhan;
                })
                .collect(Collectors.toCollection(LinkedList::new));
    }
}

package com.px.tool.domain.user.service.impl;

import com.google.common.collect.Sets;
import com.px.tool.domain.RequestType;
import com.px.tool.domain.request.NoiNhan;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.domain.user.Role;
import com.px.tool.domain.user.User;
import com.px.tool.domain.user.UserRequest;
import com.px.tool.domain.user.repository.RoleRepository;
import com.px.tool.domain.user.repository.UserRepository;
import com.px.tool.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.px.tool.domain.user.repository.UserRepository.group_12;
import static com.px.tool.domain.user.repository.UserRepository.group_17_25;
import static com.px.tool.domain.user.repository.UserRepository.group_29_40;

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

//    @Autowired
//    private PhongBanRepository phongBanRepository;

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
    public User create(UserRequest user) {
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
    public List<NoiNhan> findNoiNhan(Long userId, Long requestId) {
        User currentUser = findById(userId);
        List<User> pbs = null;
        if (Objects.isNull(requestId)) {
            pbs = userRepository.findByGroup(group_29_40);
        } else {
            // TODO: van phai check lai case kiem hong nhe'
            Request existedRequest = requestService.findById(requestId);
            if (existedRequest.getStatus() == RequestType.KIEM_HONG) {
                if (currentUser.isToTruong()) {
                    pbs = userRepository.findByGroup(group_29_40)
                            .stream()
                            .filter(el -> el.getLevel() == 4)
                            .collect(Collectors.toList());
                } else if (currentUser.isTroLyKT()) {
                    pbs = userRepository.findByGroup(group_17_25)
                            .stream()
                            .filter(el -> el.getLevel() == 3)
                            .collect(Collectors.toList());
                } else if (currentUser.isQuanDocPhanXuong()) {
                    pbs = userRepository.findByGroup(group_12)
                            .stream()
                            .filter(el -> (el.getLevel() == 3 || el.getLevel() == 4))
                            .collect(Collectors.toList());
                }
            } else if (existedRequest.getStatus() == RequestType.DAT_HANG) {
                if (currentUser.isNhanVienVatTu()) {

                } else if (currentUser.isTruongPhongVatTu()) {

                } else if (currentUser.isTroLyPhongKTHK()) {

                } else if (currentUser.isTruongPhongKTHK()) {

                }
            } else if (existedRequest.getStatus() == RequestType.PHUONG_AN) {

            } else if (existedRequest.getStatus() == RequestType.CONG_NHAN_THANH_PHAM) {

            }
        }
        if (Objects.isNull(pbs)) {
            return Collections.emptyList();
        }

        return pbs.stream()
                .map(NoiNhan::fromUserEntity)
                .sorted(Comparator.comparingLong(NoiNhan::getId))
                .collect(Collectors.toList());
    }
}

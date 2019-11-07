package com.px.tool.domain.user.service.impl;

import com.google.common.collect.Sets;
import com.px.tool.domain.RequestType;
import com.px.tool.domain.request.NoiNhan;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.domain.user.PhongBan;
import com.px.tool.domain.user.Role;
import com.px.tool.domain.user.User;
import com.px.tool.domain.user.repository.PhongBanRepository;
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

import static com.px.tool.domain.user.repository.PhongBanRepository.group_12;
import static com.px.tool.domain.user.repository.PhongBanRepository.group_17_25;
import static com.px.tool.domain.user.repository.PhongBanRepository.group_29_40;

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

    @Autowired
    private PhongBanRepository phongBanRepository;

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
    public LinkedList<NoiNhan> findNoiNhan(Long userId, Long requestId) {
        User currentUser = findById(userId);
        List<PhongBan> pbs = null;
        if (Objects.isNull(requestId)) {
            pbs = phongBanRepository.findByGroup(group_29_40);

            // current user TO TRUONG =  51->81, chuyen den 4a: 29-40`

            // TRO LY KT 4a: 29-40, chuyen den cap 3 : 17-25
            // QUAN_DOC_PX cap 3: 17-25 , chuyen den 50a, 50b, 50c

        } else {
            // TODO: van phai check lai case kiem hong nhe'
            Request existedRequest = requestService.findById(requestId);
            if (existedRequest.getStatus() == RequestType.KIEM_HONG) {
                if (currentUser.isToTruong()) {
                    pbs = phongBanRepository.findByGroup(group_29_40);
                } else if (currentUser.isTroLyKT()) {
                    pbs = phongBanRepository.findByGroup(group_17_25);
                } else if (currentUser.isQuanDocPhanXuong()) {
                    pbs = phongBanRepository.findByGroup(group_12);
                }
            }
        }

        return pbs.stream()
                .map(NoiNhan::fromPhongBanEntity)
                .collect(Collectors.toCollection(LinkedList::new));

//        return IntStream.rangeClosed(1,10)
//                .mapToObj(el -> {
//                    NoiNhan noiNhan = new NoiNhan();
//                    noiNhan.setId(Long.valueOf(el));
//                    noiNhan.setName("Name __" + el);
//                    return noiNhan;
//                })
//                .collect(Collectors.toCollection(LinkedList::new));
    }
}

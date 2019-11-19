package com.px.tool.domain.user.service.impl;

import com.google.common.collect.Sets;
import com.px.tool.domain.RequestType;
import com.px.tool.domain.request.NoiNhan;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.domain.user.NoiNhanRequestParams;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.px.tool.domain.user.repository.UserRepository.group_12;
import static com.px.tool.domain.user.repository.UserRepository.group_14;
import static com.px.tool.domain.user.repository.UserRepository.group_17_25;
import static com.px.tool.domain.user.repository.UserRepository.group_29_40;
import static com.px.tool.domain.user.repository.UserRepository.group_cac_truong_phong;
import static com.px.tool.domain.user.repository.UserRepository.group_giam_doc;
import static com.px.tool.domain.user.repository.UserRepository.group_ke_hoach;
import static com.px.tool.domain.user.repository.UserRepository.group_nv_KCS;

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
    public List<NoiNhan> findNoiNhan(Long userId, NoiNhanRequestParams requestParams) {
        User currentUser = findById(userId);
        List<User> pbs = new ArrayList<>();
        if (Objects.isNull(requestParams.getRequestId())) {
            pbs = userRepository.findByGroup(group_29_40);
        } else {
            // TODO: van phai check lai case kiem hong nhe'
            Request existedRequest = requestService.findById(requestParams.getRequestId());
            if (existedRequest.getStatus() == RequestType.KIEM_HONG) {
                filterTheoKiemHong(requestParams, currentUser, pbs);
            } else if (existedRequest.getStatus() == RequestType.DAT_HANG) {
                filterTheoDatHang(requestParams, currentUser, pbs);
            } else if (existedRequest.getStatus() == RequestType.PHUONG_AN) {
                filterTheoPhuongAn(requestParams, currentUser, pbs);
            } else if (existedRequest.getStatus() == RequestType.CONG_NHAN_THANH_PHAM) {
                filterTheoCNTP(requestParams, currentUser, pbs);
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

    private void filterTheoCNTP(NoiNhanRequestParams requestParams, User currentUser, List<User> users) {
        Stream<User> pbs = null;
        if (currentUser.isNguoiLapPhieuCNTP()) {
            pbs = userRepository.findByGroup(group_29_40)
                    .stream()
                    .filter(el -> el.getLevel() == 3);
        } else if (currentUser.isNhanVienKCS()) {
            pbs = userRepository.findByGroup(group_ke_hoach)
                    .stream()
                    .filter(el -> el.getLevel() == 4);
        } else if (currentUser.isTruongPhongKCS()) {
            pbs = userRepository.findByGroup(group_nv_KCS)
                    .stream()
                    .filter(el -> el.getLevel() == 4);
        }
        if (pbs != null) {
            users.addAll(pbs.collect(Collectors.toList()));
        }
    }

    private void filterTheoPhuongAn(NoiNhanRequestParams requestParams, User currentUser, List<User> users) {
        Stream<User> pbs = null;
        if (currentUser.isNguoiLapPhieu()) {
            pbs = userRepository.findByGroup(group_29_40)
                    .stream()
                    .filter(el -> el.getLevel() == 3);
        } else if (currentUser.isTruongPhongKTHK()) {   // chuyen 50d
            pbs = userRepository.findByGroup(group_12)
                    .stream()
                    .filter(el -> el.getLevel() == 4);
        } else if (currentUser.isNhanVienTiepLieu()) {
            pbs = userRepository.findByGroup(group_12)
                    .stream()
                    .filter(el -> el.getLevel() == 3);
        } else if (currentUser.isTruongPhongVatTu()) { // chuyen 50e
            pbs = userRepository.findByGroup(group_14)
                    .stream()
                    .filter(el -> el.getLevel() == 4);
        } else if (currentUser.isNhanVienDinhMuc()) { // chuyen truong phong ke hoach
            pbs = userRepository.findByGroup(group_14)
                    .stream()
                    .filter(el -> el.getLevel() == 3);
        } else if (currentUser.isTruongPhongKeHoach()) {
            pbs = userRepository.findByGroup(group_giam_doc).stream();
        } else if (currentUser.getLevel() == 2) {
            pbs = userRepository.findByGroup(group_cac_truong_phong).stream();
        }
        if (pbs != null) {
            users.addAll(pbs.collect(Collectors.toList()));
        }
    }

    private void filterTheoDatHang(NoiNhanRequestParams requestParams, User currentUser, List<User> users) {
        Stream<User> pbs = null;
        if (currentUser.isNhanVienVatTu()) {
            pbs = userRepository.findByGroup(group_12).stream();
        } else if (currentUser.isTruongPhongVatTu()) {
            if (!requestParams.getTpVatTu()) {
                pbs = userRepository.findByGroup(group_12).stream()
                        .filter(el -> el.getLevel() == 4);
            } else {
                pbs = userRepository.findByGroup(group_29_40).stream()
                        .filter(el -> el.getLevel() == 4);
            }

        } else if (currentUser.isTroLyPhongKTHK()) {
            pbs = userRepository.findByGroup(group_29_40).stream()
                    .filter(el -> el.getLevel() == 3);
        } else if (currentUser.isTruongPhongKTHK()) {
            //TODO: neu truong phong kthk dong y thi tao van ban den
            pbs = userRepository.findByGroup(group_29_40).stream()
                    .filter(el -> el.getLevel() == 4);
        }
        if (pbs != null) {
            users.addAll(pbs.collect(Collectors.toList()));
        }
    }

    private void filterTheoKiemHong(NoiNhanRequestParams requestParams, User currentUser, List<User> users) {
        Stream<User> pbs = null;
        if (currentUser.isToTruong()) {
            pbs = userRepository.findByGroup(group_29_40)
                    .stream()
                    .filter(el -> el.getLevel() == 4);
        } else if (currentUser.isTroLyKT()) {
            pbs = userRepository.findByGroup(group_17_25)
                    .stream()
                    .filter(el -> el.getLevel() == 3);
        } else if (currentUser.isQuanDocPhanXuong()) {
            if (requestParams.getQuanDoc()) {
                pbs = userRepository.findByGroup(group_12)
                        .stream()
                        .filter(el -> el.getLevel() == 4);
            } else {
                pbs = userRepository.findByGroup(group_29_40)
                        .stream()
                        .filter(el -> el.getLevel() == 4);
            }
        }
        if (pbs != null) {
            users.addAll(pbs.collect(Collectors.toList()));
        }
    }

    @Override
    public List<NoiNhan> findVanBanDenNoiNhan() {
        List<User> users = userRepository.findAll();
        if (users != null) {
            return users.stream()
                    .map(NoiNhan::fromUserEntity)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}

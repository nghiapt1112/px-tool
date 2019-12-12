package com.px.tool.domain.user.service.impl;

import com.google.common.collect.Sets;
import com.px.tool.domain.RequestType;
import com.px.tool.domain.phuongan.service.PhuongAnService;
import com.px.tool.domain.request.NguoiDangXuLy;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.payload.NoiNhan;
import com.px.tool.domain.request.payload.PhanXuongPayload;
import com.px.tool.domain.request.payload.ToSXPayload;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.domain.user.Role;
import com.px.tool.domain.user.User;
import com.px.tool.domain.user.payload.NoiNhanRequestParams;
import com.px.tool.domain.user.payload.UserPageRequest;
import com.px.tool.domain.user.payload.UserPageResponse;
import com.px.tool.domain.user.payload.UserPayload;
import com.px.tool.domain.user.payload.UserRequest;
import com.px.tool.domain.user.repository.UserRepository;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.infrastructure.CacheService;
import com.px.tool.infrastructure.exception.PXException;
import com.px.tool.infrastructure.logger.PXLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.px.tool.domain.user.repository.UserRepository.group_12;
import static com.px.tool.domain.user.repository.UserRepository.group_14;
import static com.px.tool.domain.user.repository.UserRepository.group_17_25;
import static com.px.tool.domain.user.repository.UserRepository.group_29_40;
import static com.px.tool.domain.user.repository.UserRepository.group_KCS;
import static com.px.tool.domain.user.repository.UserRepository.group_giam_doc;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RequestService requestService;

    @Autowired
    private PhuongAnService phuongAnService;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private PhongBanServiceImpl phongBanService;

    @Autowired
    private CacheService cacheService;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + username));
    }

    @Override
    public List<User> findAll() {
        PXLogger.info("Fetching all users");
        return cacheService.getUsers_cache();
    }

    @Override
    public UserPageResponse findUsers(UserPageRequest request) {
        Page<User> page = userRepository.findAll(request.toPageRequest());

        UserPageResponse usersPage = new UserPageResponse(request.getPage(), request.getSize());

        usersPage.setDetails(page.stream()
                .map(UserPayload::fromEntityNoImg) // each user -> userPayload to view on paging/sorting
                .collect(Collectors.toCollection(() -> new ArrayList<>((int) page.getTotalElements())))
        );
        return usersPage;
    }

    @Override
    @Transactional
    public User create(UserRequest user) {
        Role role = roleService.findById(Long.valueOf(user.getLevel()));

        if (user.getUserId() == null && userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email này đã tồn tại");
        }
        if (user.getUserId() != null) {
            userRepository.updateUserInfo(user.getFullName(), user.getImgBase64(), user.getUserId());
            return user.toUserEntity();
        }
        User entity = user.toUserEntity();
        if (user.getUserId() == null) {
            entity.setPassword(passwordEncoder.encode(user.getPassword()));
            entity.setAuthorities(Sets.newHashSet(role));
        }
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
        if (userById().containsKey(userId)) {
            return userById().get(userId);
        } else {
            throw new PXException("Không tìm thấy User với Id = " + userId);
        }
    }

    @Override
    public List<User> findByIds(Collection<Long> userIds) {
        return userRepository.findByIds(userIds);
    }

    @Override
    public List<NoiNhan> findNoiNhan(Long userId, NoiNhanRequestParams requestParams) {
        User currentUser = findById(userId);
        List<User> pbs = new ArrayList<>();
        if (Objects.isNull(requestParams.getRequestId())) {
            pbs = userRepository.findByGroup(group_29_40);
        } else {
            if (requestParams.getType() == RequestType.PHUONG_AN) {
                filterTheoPhuongAn(requestParams, currentUser, pbs);
            } else if (requestParams.getType() == RequestType.CONG_NHAN_THANH_PHAM) {
                filterTheoCNTP(requestParams, currentUser, pbs);
            } else {
                Request existedRequest = requestService.findById(requestParams.getRequestId());
                if (existedRequest.getStatus() == RequestType.KIEM_HONG) {
                    filterTheoKiemHong(requestParams, currentUser, pbs);
                } else if (existedRequest.getStatus() == RequestType.DAT_HANG) {
                    filterTheoDatHang(requestParams, currentUser, pbs);
                }
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
        if (currentUser.isQuanDocPhanXuong()) {
            // chuyen cho cac to truong
            pbs = userRepository.findByGroup(group_17_25).stream().filter(el -> el.getLevel() == 5);
        } else if (currentUser.isToTruong()) {
            // chuyen cho cac nhan vien kcs
            pbs = userRepository.findByGroup(group_KCS).stream().filter(el -> el.getLevel() == 3);
        } else if (currentUser.isNhanVienKCS()) {
            // khong chuyen di dau ca, vi khi all nhanvienKSC ok thi tu chuyen len truong phong Kcs
        } else if (currentUser.isTruongPhongKCS()) {
            // chuyen lai cho px
            pbs = userRepository.findByGroup(group_17_25).stream().filter(el -> el.getLevel() == 3);
        }

        if (pbs != null) {
            users.addAll(pbs.collect(Collectors.toList()));
        }
    }

    private void filterTheoPhuongAn(NoiNhanRequestParams requestParams, User currentUser, List<User> users) {
        Stream<User> pbs = null;
        NguoiDangXuLy nguoiDangXuLy = phuongAnService.findNguoiDangXuLy(requestParams.getRequestId());
        if (currentUser.isNguoiLapPhieu()) {
            pbs = userRepository.findByGroup(group_29_40).stream().filter(el -> el.getLevel() == 3);
        } else if (currentUser.isTruongPhongKTHK()) {   // chuyen 50d
            if (requestParams.getTpKTHK()) {
                pbs = userRepository.findByGroup(group_12).stream().filter(el -> el.getLevel() == 4);
            } else {
                // TODO: chuyen ve nguoi lap phieu
                pbs = toUserStream(nguoiDangXuLy.getNguoiLap());
            }
        } else if (currentUser.isNhanVienTiepLieu()) {
            pbs = userRepository.findByGroup(group_12).stream().filter(el -> el.getLevel() == 3);
        } else if (currentUser.isTruongPhongVatTu()) {
            if (requestParams.getTpVatTu()) {
                // nhan vien dinh muc (phong ke hoach)
                pbs = userRepository.findByGroup(group_14).stream().filter(el -> el.getLevel() == 4);
            } else {
                // TODO chuyen nhan vien tiep lieu (nhung dang khong luu nen chuyen ve luon nguoi lap)
                pbs = toUserStream(nguoiDangXuLy.getTpKTHK());
            }
        } else if (currentUser.isNhanVienDinhMuc()) { // chuyen truong phong ke hoach
            pbs = userRepository.findByGroup(group_14).stream().filter(el -> el.getLevel() == 3);
        } else if (currentUser.isTruongPhongKeHoach()) {
            if (requestParams.getTpKeHoach()) {
                pbs = userRepository.findByGroup(group_giam_doc).stream(); // chuyen len giam doc
            } else {
                // TODO:chuyen nhan vien dinh muc
                pbs = userRepository.findByGroup(group_14).stream().filter(el -> el.getLevel() == 4).limit(1);
            }
        } else if (currentUser.getLevel() == 2) { // chuyen den Nguoi thuc hien (nguoi thuc hien la cac PX)
            // TODO: neu giam doc khong dong y, chuyen ve TPKTHK/XMDC:
            if (!requestParams.getGiamDoc()) {
                pbs = toUserStream(nguoiDangXuLy.getTpKTHK());
            } else {
                pbs = userRepository.findByGroup(group_17_25).stream().filter(el -> el.getLevel() == 3);
            }
        }
        if (pbs != null) {
            users.addAll(pbs.collect(Collectors.toList()));
        }
    }

    Stream<User> toUserStream(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            return Stream.of(userOpt.get());
        } else {
            return Stream.empty();
        }
    }

    private void filterTheoDatHang(NoiNhanRequestParams requestParams, User currentUser, List<User> users) {
        Stream<User> pbs = null;
        if (currentUser.isNhanVienVatTu()) {
            pbs = userRepository.findByGroup(group_12).stream();
        } else if (currentUser.isTruongPhongVatTu()) {
            if (!requestParams.getTpVatTu()) {
//                pbs = userRepository.findByGroup(group_12).stream()
//                        .filter(el -> el.getLevel() == 4);
                pbs = Stream.of(userById().get(requestService.findById(requestParams.getRequestId()).getPhieuDatHang().getNguoiDatHangId()));
            } else {
                pbs = userRepository.findByGroup(group_29_40).stream()
                        .filter(el -> el.getLevel() == 4);
            }
        } else if (currentUser.isTroLyPhongKTHK()) {
            pbs = userRepository.findByGroup(group_29_40).stream()
                    .filter(el -> el.getLevel() == 3);
        } else if (currentUser.isTruongPhongKTHK()) {
            //TODO: neu truong phong kthk dong y thi tao van ban den + PA
//            pbs = userRepository.findByGroup(group_29_40).stream()
//                    .filter(el -> el.getLevel() == 4);
            if (!requestParams.getTpKTHK()) {
                pbs = Stream.of(userById().get(requestService.findById(requestParams.getRequestId()).getPhieuDatHang().getTpvatTuId()));
            } else {
                pbs = Stream.empty();
            }
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
            if (requestParams.getTroLyKT()) {
//                pbs = userRepository.findByGroup(group_17_25)
//                        .stream()
//                        .filter(el -> el.getLevel() == 3);
                pbs = Stream.of(userById().get(requestService.findById(requestParams.getRequestId()).getKiemHong().getPhanXuong()));
            } else {
                pbs = Stream.of(userById().get(requestService.findById(requestParams.getRequestId()).getKiemHong().getToTruongId()));
            }
        } else if (currentUser.isQuanDocPhanXuong()) {
            if (requestParams.getQuanDoc()) {
                pbs = userRepository.findByGroup(group_12)
                        .stream()
                        .filter(el -> el.getLevel() == 4);
            } else {
                pbs = Stream.of(userById().get(requestService.findById(requestParams.getRequestId()).getKiemHong().getTroLyId()));
            }
        }
        if (pbs != null) {
            users.addAll(pbs.collect(Collectors.toList()));
        }
    }

    @Override
    public List<NoiNhan> findVanBanDenNoiNhan() {
        return cacheService.getUsers_cache()
                .stream()
                .map(NoiNhan::fromUserEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<PhanXuongPayload> findListPhanXuong(Long userId) {
        try {
            return userRepository.findByGroup(Arrays.asList(findById(userId).getPhongBan().getPhongBanId()))
                    .stream()
                    .filter(el -> el.getLevel() == 3)
                    .map(PhanXuongPayload::fromUserEntity)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return PhanXuongPayload.emptyList;
        }
    }

    @Override
    public List<PhanXuongPayload> findNguoiThucHien() {
        return userRepository.findByGroup(group_17_25)
                .stream()
                .filter(el -> el.getLevel() == 3)
                .map(PhanXuongPayload::fromUserEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ToSXPayload> findListToSanXuat(Long pxId) {
        return userRepository.findByGroup(Arrays.asList(pxId))
                .stream()
                .filter(el -> el.getLevel() == 5)
                .map(ToSXPayload::fromUserEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Map<Long, User> userById() {
        return cacheService.getUserById_cache();
    }

    @Override
    @Transactional
    public void taoUser(UserRequest user) {
        Role role = roleService.findById(Long.valueOf(user.getLevel()));

        User entity = user.toUserEntity();
        entity.setPassword(passwordEncoder.encode(user.getPassword()));
        entity.setAuthorities(Sets.newHashSet(role));
        entity.setPhongBan(phongBanService.findById(user.getPhongBanId()));

        userRepository.save(entity);
        cacheService.clearCache(CacheService.CACHE_USER);
    }

}

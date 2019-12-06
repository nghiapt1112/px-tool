package com.px.tool;

import com.px.tool.domain.user.PhongBan;
import com.px.tool.domain.user.Role;
import com.px.tool.domain.user.User;
import com.px.tool.domain.user.payload.UserRequest;
import com.px.tool.domain.user.repository.PhongBanRepository;
import com.px.tool.domain.user.repository.RoleRepository;
import com.px.tool.domain.user.repository.UserRepository;
import com.px.tool.domain.user.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UserRepositoryTest extends PxApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PhongBanRepository phongBanRepository;

    @Test
    public void createRole() {
        Role role = new Role();
        role.setAuthority("ADMIN");
        roleRepository.save(role);

        Role role2 = new Role();
        role2.setAuthority("USER");
        roleRepository.save(role2);
    }

    @Test
    public void create() {
        UserRequest user = new UserRequest();
        user.setEmail("admin");
        user.setPassword("123");
        user.setLevel(1);
        this.userService.create(user);
    }

    @Test
    public void createPhongBan() {
        phongBanRepository.saveAll(
                IntStream.rangeClosed(1, 80)
                        .mapToObj(el -> {
                            PhongBan phongBan = new PhongBan();
                            phongBan.setName("email__" + el);
                            phongBan.setGroup(5);
                            return phongBan;
                        })
                        .collect(Collectors.toList())
        );
    }

    @Test
    public void createUsers() {
        IntStream.rangeClosed(1, 67)
                .mapToObj(el -> {
                    UserRequest userRequest = new UserRequest();
                    userRequest.setPassword("123");
                    userRequest.setEmail("email_" + el);
                    userRequest.setLevel(5);
                    userRequest.setImgBase64("data:image/png;base64,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");
                    return userRequest;
                })
                .forEach(el -> this.userService.create(el));
    }

    @Test
    public void findUsers() {
        boolean wrongData = false;
        for (User el : userService.findUsers()) {
            if (el != null && CollectionUtils.isEmpty(el.getAuthorities())) {
                wrongData = true;
            }
        }
        Assert.assertEquals(false, wrongData);
    }

    @Test
    public void findByPhongBan() {
        List<User> users = userRepository.findByGroup(UserRepository.group_29_40);
        System.out.println(users.size());
    }

}
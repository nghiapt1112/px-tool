package com.px.tool;

import com.px.tool.model.PhongBan;
import com.px.tool.model.Role;
import com.px.tool.model.request.UserRequest;
import com.px.tool.repository.PhongBanRepository;
import com.px.tool.repository.RoleRepository;
import com.px.tool.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UserRepositoryTest extends PxApplicationTests {

    @Autowired
    private UserService userService;

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
        user.setUserName("admin");
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
                            phongBan.setName("");
                            phongBan.setLevel(2);
                            return phongBan;
                        })
                        .collect(Collectors.toList())
        );
    }

    @Test
    public void createUsers() {
        IntStream.rangeClosed(1,22)
                .mapToObj(el -> {
                    UserRequest userRequest = new UserRequest();
                    userRequest.setPassword("123");
                    userRequest.setUserName("");
                    userRequest.setLevel(2);
                    return userRequest;
                })
                .forEach(el -> this.userService.create(el));
    }
}

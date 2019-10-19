package com.px.tool;

import com.px.tool.model.Role;
import com.px.tool.model.User;
import com.px.tool.model.request.UserRequest;
import com.px.tool.repository.RoleRepository;
import com.px.tool.service.UserService;
import org.apache.commons.compress.utils.Sets;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.stream.IntStream;

public class UserRepositoryTest extends PxApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void createRole() {
        Role role = new Role();
        role.setAuthority("USER");
        roleRepository.save(role);
    }

    @Test
    public void create() {
        UserRequest user = new UserRequest();
        user.setUserName("admin");
        user.setPassword("123");
        this.userService.create(user);
    }
}

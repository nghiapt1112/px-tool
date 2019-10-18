package com.px.tool;

import com.px.tool.model.Role;
import com.px.tool.model.User;
import com.px.tool.repository.RoleRepository;
import com.px.tool.repository.UserRepository;
import org.apache.commons.compress.utils.Sets;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.stream.IntStream;

public class UserRepositoryTest extends PxApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void createRole() {
        Role role = new Role();
        role.setAuthority("ADMIN");
        Role roleSaved = roleRepository.save(role);
    }

    @Test
    public void create() {
        Role role = roleRepository.findById(1L).get();
        HashSet<Role> sets = Sets.newHashSet(role);
        IntStream.range(1, 12)
                .forEach(el -> {
                    User user = new User();
                    user.setEmail("user_" + el + "@mail.com");
                    user.setPassword("password");
//                    user.setAuthorities(sets);
                    this.userRepository.save(user);
                });
    }
}

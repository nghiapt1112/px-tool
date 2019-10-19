package com.px.tool;

import com.google.common.collect.Sets;
import com.px.tool.model.Role;
import com.px.tool.model.User;
import com.px.tool.model.request.UserRequest;
import com.px.tool.repository.RoleRepository;
import com.px.tool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
public class PxApplication {

    public static void main(String[] args) {
        SpringApplication.run(PxApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

//    @PostConstruct
//    public void initData() {
//        List<Role> roles = roleRepository.findAll();
//        Role adminRole = null;
//        if (roles.isEmpty()) {
//            adminRole = roleRepository.save(new Role("ADMIN"));
//            roleRepository.save(new Role("USER"));
//        }
//        List<User> users = userService.findUsers();
//        if (users.isEmpty()) {
//            UserRequest su = new UserRequest();
//            su.setPassword("123");
//            su.setUserName("admin");
//            userService.create(su);
//        }
//    }
}

package com.mas;


import com.mas.dao.repository.RoleRepository;
import com.mas.dao.repository.UserRepository;
import com.mas.domain.Role;
import com.mas.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StartUpInit {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void init() {
        Role role = roleRepository.findRoleByRoleName("ROLE_ADMIN");
        if (role == null) {
            Role admin = roleRepository.save(new Role("ROLE_ADMIN"));
            Role moderator = roleRepository.save(new Role("ROLE_MODERATOR"));
            Role user = roleRepository.save(new Role("ROLE_USER"));
            User adminUser = new User();
            adminUser.setEmail("denis.sosulev@gmail.com");
            String password = bCryptPasswordEncoder.encode("admin");
            adminUser.setPassword(password);
            adminUser.setLogin("admin");
            adminUser.setUserName("admin");
            adminUser.setLastName("admin");
            adminUser.setFbId("2047007298847422");
            adminUser.setEnabled(true);
            adminUser.getRoles().add(admin);
            adminUser.getRoles().add(moderator);
            adminUser.getRoles().add(user);
            admin.getUsers().add(adminUser);
            moderator.getUsers().add(adminUser);
            user.getUsers().add(adminUser);
            userRepository.save(adminUser);
        }
    }
}

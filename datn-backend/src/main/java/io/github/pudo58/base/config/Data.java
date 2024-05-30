package io.github.pudo58.base.config;

import io.github.pudo58.base.entity.Role;
import io.github.pudo58.base.entity.User;
import io.github.pudo58.base.repo.RoleRepo;
import io.github.pudo58.base.repo.UserRepo;
import io.github.pudo58.constant.UserConst;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class Data {
    @Autowired
    UserRepo userRepository;
    @Autowired
    RoleRepo roleRepo;
    private final Logger _log = LogManager.getLogger(Data.class);

    @Bean
    public CommandLineRunner hehe() {
        return args -> {
            try {
                PasswordEncoder encoder = new BCryptPasswordEncoder();
                Set<Role> roleList = new HashSet<>();

                User newAdmin = User.builder().username("admin").password(encoder.encode("123456")).build();
                Role role = roleRepo.findByName(UserConst.ROLE_ADMIN);
                if (role == null){
                    role = new Role();
                    role.setName(UserConst.ROLE_ADMIN);
                    roleRepo.save(role);
                }
                roleList.add(role);
                User existUser = userRepository.findByUsername(newAdmin.getUsername());
                if (existUser != null){
                    existUser.setRoleList(roleList);
                    userRepository.save(existUser);
                }
                else{
                    userRepository.save(newAdmin);
                }
            }
            catch (Exception e){
                _log.error(e);
            }
        };
    }
}

package io.github.pudo58.base.config;

import com.corundumstudio.socketio.SocketIOServer;
import io.github.pudo58.base.entity.Role;
import io.github.pudo58.base.entity.User;
import io.github.pudo58.base.override.CustomAuthenticationEntryPoint;
import io.github.pudo58.base.repo.RoleRepo;
import io.github.pudo58.base.repo.UserRepo;
import io.github.pudo58.constant.UserConst;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashSet;
import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    @Transactional
    public CommandLineRunner commandLineRunner() {
        return args -> {
            List<Role> roleList = this.roleRepo.findAll((root, query, builder) -> builder.in(root.get("name")).value(UserConst.ROLE_LIST));
            if (roleList.isEmpty()) {
                for (String role : UserConst.ROLE_LIST) {
                    Role roleEntity = new Role();
                    roleEntity.setName(role);
                    this.roleRepo.save(roleEntity);
                }
            }

            boolean isExist = this.userRepo.findByUsername("admin") != null;
            if (!isExist) {
                User user = new User();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("123456"));
                user.setRoleList(new HashSet<>(roleList));
                user.setStatus(UserConst.STATUS_ACTIVE);
                this.userRepo.save(user);
            }
        };
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
        converters.add(new MappingJackson2XmlHttpMessageConverter());
    }

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname("localhost");
        config.setPort(9090);
        SocketIOServer server = new SocketIOServer(config);
        server.start();
        server.addConnectListener(client -> log.info("Client connected: " + client.getSessionId()));
        return server;
    }
}

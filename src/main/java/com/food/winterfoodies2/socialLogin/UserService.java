package com.food.winterfoodies2.socialLogin;

import com.food.winterfoodies2.entity.Role;
import com.food.winterfoodies2.entity.User;
import com.food.winterfoodies2.repository.RoleRepository;
import com.food.winterfoodies2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long createUser(String email, String nickname, String id, String oauthName) {
        // Check if user already exists
        Optional<User> existingUser = userRepository.findByEmailAndOauthId(email, id);

        User user;
        Role role;
        if (existingUser.isPresent()) {
            // User already exists, update their information
            user = existingUser.get();
            user.setUsername(nickname);
            log.info("회원 정보 업데이트 완료");
        } else {
            // User does not exist, create a new user
            user = User.builder()
                    .email(email)
                    .username(nickname)
                    .password(passwordEncoder.encode("1234"))
                    .oauth(oauthName)
                    .oauthId(id)
                    .build();
            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName("ROLE_USER");
            roles.add(userRole);

            user.setRoles(roles);

            userRepository.save(user);
            log.info("새로운 회원 저장 완료");
        }

        return user.getId();
    }
}

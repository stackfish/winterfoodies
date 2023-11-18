package com.food.winterfoodies2.service.impl;

import com.food.winterfoodies2.dto.account.PasswordChangeRequest;
import com.food.winterfoodies2.dto.account.UserResponseDto;
import com.food.winterfoodies2.entity.Role;
import com.food.winterfoodies2.entity.Store;
import com.food.winterfoodies2.entity.User;
import com.food.winterfoodies2.repository.RoleRepository;
import com.food.winterfoodies2.repository.UserRepository;
import com.food.winterfoodies2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserResponseDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new UserResponseDto(user);
    }

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
    @Override
    public void changePassword(PasswordChangeRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + request.getUsername()));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public List<Store> getFavoriteStores(String username) {
        return null;
    }


}

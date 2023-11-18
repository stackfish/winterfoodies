package com.food.winterfoodies2.service.impl;

import com.food.winterfoodies2.dto.account.*;
import com.food.winterfoodies2.entity.Role;
import com.food.winterfoodies2.entity.User;
import com.food.winterfoodies2.exception.EmailAlreadyExistsException;
import com.food.winterfoodies2.exception.TodoAPIException;
import com.food.winterfoodies2.exception.UserNotFoundException;
import com.food.winterfoodies2.repository.RoleRepository;
import com.food.winterfoodies2.repository.UserRepository;
import com.food.winterfoodies2.security.JwtTokenProvider;
import com.food.winterfoodies2.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    private final String FROM_PHONE_NUMBER = "18185784134";
    @Value("${twilio.account_sid}")
    public String ACCOUNT_SID;
    @Value("${twilio.auth_token}")
    public String AUTH_TOKEN;

    @Override
    public String register(RegisterDto registerDto) {

        // check username is already exists in database

        // check email is already exists in database
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        // verify auth code

        User user = new User();
        user.setPhoneNumber(registerDto.getPhoneNumber());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setUsername(registerDto.getUsername());

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER");
        roles.add(userRole);

        user.setRoles(roles);

        userRepository.save(user);

        return "User Registered Successfully!.";
    }


    private Map<String, String> authCodes = new HashMap<>();
    @Override
    public String sendAuthCode(SendAuthCodeDto sendAuthCodeDto) {
//        String authCode = generateAuthCode();
        authCodes.put(sendAuthCodeDto.getPhoneNumber(), "123456");

//        // Twilio 초기화
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//
//        // SMS 메시지 전송
//        Message.creator(
//                        new com.twilio.type.PhoneNumber(sendAuthCodeDto.getPhoneNumber()),  // To
//                        new com.twilio.type.PhoneNumber(FROM_PHONE_NUMBER),  // From
//                        "Your auth code is: " + authCode)
//                .create();

        return "인증번호가 전송되었습니다.";
    }

    @Override
    public String sendAuthCodeFind(SendAuthCodeDto sendAuthCodeDto) {
        User user = userRepository.findByPhoneNumber(sendAuthCodeDto.getPhoneNumber())
                .orElseThrow(() -> new UserNotFoundException("등록된 휴대폰번호가 없습니다."));

        user.setAuthCode("123456");
        userRepository.save(user);

        return "인증번호가 전송되었습니다.";
    }


//    private String generateAuthCode() {
//        Random random = new Random();
//        return String.format("%06d", random.nextInt(1000000));
//    }

    @Override
    public FindIdResponseDto findId(FindIdDto findIdDto) {
        Optional<User> userOpt = userRepository.findByPhoneNumber(findIdDto.getPhoneNumber());

        if (!userOpt.isPresent()) {
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Phone number does not exist.");
        }

        // 휴대전화 인증 코드 확인
        boolean isVerified = this.verifyAuthCode(findIdDto.getPhoneNumber());
        if (!isVerified) {
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Invalid auth code.");
        }

        User user = userOpt.get();

        // 가입일 형식 설정
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(user.getJoinDate());

        return new FindIdResponseDto(user.getEmail(), formattedDate);
    }

    @Override
    public boolean verifyAuthCode(String phoneNumber) {


        String code = authCodes.get(phoneNumber);
        if(code != null && code.equals("123456")){
            return true;
        }
        return false;
    }

    @Override
    public User verifyUser(FindPasswordDto findPasswordDto) {
        User user = userRepository.findByEmailAndUsername(findPasswordDto.getEmail(), findPasswordDto.getUsername());

        if (user == null) {
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Email and username do not match.");
        }

        // 휴대전화 인증 코드 확인
        boolean isVerified = this.verifyAuthCode(user.getPhoneNumber());
        if (!isVerified) {
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Invalid auth code.");
        }

        return user;
    }

    @Override
    public String resetPassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + userId));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return "Password has been reset successfully.";
    }

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword(),
                Collections.emptyList()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        Optional<User> userOptional = userRepository.findByUsernameOrEmail(loginDto.getEmail(), loginDto.getEmail());

        String role = null;
        if(userOptional.isPresent()){
            User loggedInUser = userOptional.get();
            Optional<Role> optionalRole = loggedInUser.getRoles().stream().findFirst();

            if(optionalRole.isPresent()){
                Role userRole = optionalRole.get();
                role = userRole.getName();
            }
        }

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setRole(role);
        jwtAuthResponse.setAccessToken(token);

        return jwtAuthResponse;
    }
}

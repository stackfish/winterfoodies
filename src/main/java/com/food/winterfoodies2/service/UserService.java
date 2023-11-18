package com.food.winterfoodies2.service;

import com.food.winterfoodies2.dto.account.PasswordChangeRequest;
import com.food.winterfoodies2.dto.account.UserResponseDto;
import com.food.winterfoodies2.entity.Store;

import java.util.List;

public interface UserService {

   UserResponseDto getUserByUsername(String username);

    Long createUser(String email, String nickname, String id, String oauthName);

    void changePassword(PasswordChangeRequest request);

    List<Store> getFavoriteStores(String username);
}

package com.food.winterfoodies2.controller;

import static org.mockito.Mockito.when;

import com.food.winterfoodies2.security.JwtTokenProvider;
import com.food.winterfoodies2.service.MyPageService;
import com.food.winterfoodies2.service.UserService;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {MyPageController.class})
@ExtendWith(SpringExtension.class)
class MyPageControllerDiffblueTest {
    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MyPageController myPageController;

    @MockBean
    private MyPageService myPageService;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link MyPageController#getFavoriteStores(String)}
     */
    @Test
    void testGetFavoriteStores() throws Exception {
        when(jwtTokenProvider.getUserClaims(Mockito.<String>any()))
                .thenReturn(new JwtTokenProvider.UserClaims(1L, "janedoe"));
        when(myPageService.getFavoriteStores(Mockito.<Long>any())).thenReturn(new HashSet<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/mypage/favorite")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
        MockMvcBuilders.standaloneSetup(myPageController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"status\":\"success\",\"data\":[]}"));
    }
}

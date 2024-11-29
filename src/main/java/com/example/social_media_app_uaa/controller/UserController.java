package com.example.social_media_app_uaa.controller;

import com.example.social_media_app_uaa.common.Common;
import com.example.social_media_app_uaa.dto.*;
import com.example.social_media_app_uaa.entity.UserEntity;
import com.example.social_media_app_uaa.repository.UserRepository;
import com.example.social_media_app_uaa.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// https://blogs.perficient.com/2020/07/27/requestbody-and-multipart-on-spring-boot/
@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @Operation(summary = "Api hỗ trợ lấy danh sách người dùng trên app")
    @GetMapping("/tiny-3/list")
    public Page<UserEntity> getUsers2By(@RequestParam(required = false) String search,
                                       @RequestParam(required = false) List<Long> notIds,
                                       @ParameterObject Pageable pageable){
        return userService.getUsers2By(search, notIds, pageable);
    }

    @Operation(summary = "Lấy danh sách user dựa vào")
    @GetMapping("/tiny-2/list")
    public Page<UserEntity> getUsersBy(@RequestParam(required = false) String search,
                                       @RequestParam(required = false) List<Long> ids,
                                       @ParameterObject Pageable pageable){
        return userService.getUsersBy(search, ids, pageable);
    }

    @Operation(summary = "Lấy danh sách user dựa vào ids")
    @GetMapping("/tiny/list")
        public List<UserEntity> getUsersBy(@RequestParam List<Long> ids){
        return userRepository.findAllByIdIn(ids);
    }

    @Operation(summary = "Lấy thông tin cá nhân")
    @GetMapping
    public UserOutputV2 getUserInformation(@RequestHeader(value = Common.AUTHORIZATION) String accessToken){
        return userService.getUserInformation(accessToken);
    }

    // 2024-03-20T17:04:52.755Z
    @Operation(summary = "Thay đổi thông tin cá nhân")
    @PostMapping(value = "/change-user-information", consumes = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public void changeUserInformation(@RequestPart("new_user_info") @Valid String changeInfoUserRequestString,
                                      @RequestHeader(value = Common.AUTHORIZATION) String accessToken,
                                      @RequestPart("image") MultipartFile multipartFile) throws JsonProcessingException {
        ChangeInfoUserRequest changeInfoUserRequest;
        ObjectMapper objectMapper = new ObjectMapper();
        changeInfoUserRequest = objectMapper.readValue(changeInfoUserRequestString, ChangeInfoUserRequest.class);
        userService.changeUserInformation(changeInfoUserRequest, accessToken, multipartFile);
    }

    @Operation(summary = "Đăng ký tài khoản")
    @PostMapping("sign-up")
    public ResponseEntity signUp(@RequestBody UserRequest signUpRequest){
        return new ResponseEntity(new TokenResponse( userService.signUp(signUpRequest)), HttpStatus.OK);
    }

    @Operation(summary = "Đăng nhập")
    @PostMapping("log-in")
    public ResponseEntity logIn(@RequestBody @Valid UserLogin logInRequest){
        return new ResponseEntity(new TokenResponse(userService.logIn(logInRequest)), HttpStatus.OK);
    }
}

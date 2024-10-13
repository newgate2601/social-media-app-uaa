package com.example.social_media_app_uaa.service;

import com.example.social_media_app_uaa.common.Common;
import com.example.social_media_app_uaa.dto.ChangeInfoUserRequest;
import com.example.social_media_app_uaa.dto.UserOutputV2;
import com.example.social_media_app_uaa.dto.UserRequest;
import com.example.social_media_app_uaa.entity.UserEntity;
import com.example.social_media_app_uaa.mapper.UserMapper;
import com.example.social_media_app_uaa.repository.UserRepository;
import com.example.social_media_app_uaa.security.TokenHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
@Configuration
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TokenHelper tokenHelper;
    private final PasswordEncoder passwordEncoder;

    private UserEntity getUserBy(Long userId){
        return userRepository.findById(userId).orElseThrow(
                RuntimeException::new
        );
    }

    @Transactional(readOnly = true)
    public UserOutputV2 getUserInformation(String accessToken){
        Long userId = tokenHelper.getUserIdFromToken(accessToken);
        UserEntity userEntity = getUserBy(userId);
        return userMapper.getOutputFromEntity(userEntity);
    }

    @Transactional
    public void changeUserInformation(ChangeInfoUserRequest changeInfoUserRequest,
                                      String accessToken,
                                      MultipartFile multipartFile){
        Long userId = tokenHelper.getUserIdFromToken(accessToken);
        UserEntity userEntity = getUserBy(userId);
        userMapper.updateEntityFromInput(userEntity, changeInfoUserRequest);
        userEntity.setBirthday(OffsetDateTime.parse(changeInfoUserRequest.getBirthdayString()));
//        userEntity.setImageUrl(CloudinaryHelper.uploadAndGetFileUrl(multipartFile));
        userRepository.save(userEntity);
    }

    @Transactional
    public String signUp(UserRequest signUpRequest){
        if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))){
            throw new RuntimeException(Common.USERNAME_IS_EXISTS);
        }
        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        UserEntity userEntity = userMapper.getEntityFromRequest(signUpRequest);
        userEntity.setImageUrl(Common.DEFAULT_IMAGE_URL);
        UUID uuid = UUID.randomUUID();
        userEntity.setFullName(Common.USER + "_" + uuid);
        userRepository.save(userEntity);
        return tokenHelper.generateToken(userEntity);
    }

    @Transactional(readOnly = true)
    public String logIn(UserRequest logInRequest){
        UserEntity userEntity = userRepository.findByUsername(logInRequest.getUsername());
        if (Objects.isNull(userEntity)){
            throw new RuntimeException(Common.INCORRECT_PASSWORD);
        }
        String currentHashedPassword = userEntity.getPassword(); // password DB
        if (passwordEncoder.matches(logInRequest.getPassword(), currentHashedPassword)){
            String accessToken = tokenHelper.generateToken(userEntity);
            String a = "Bearer " + accessToken;
            log.error("user_id = " + tokenHelper.getUserIdFromToken(a));
            return accessToken;
        }
        throw new RuntimeException(Common.INCORRECT_PASSWORD);
    }
}

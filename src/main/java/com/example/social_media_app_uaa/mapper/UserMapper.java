package com.example.social_media_app_uaa.mapper;

import com.example.social_media_app_uaa.dto.ChangeInfoUserRequest;
import com.example.social_media_app_uaa.dto.UserOutputV2;
import com.example.social_media_app_uaa.dto.UserRequest;
import com.example.social_media_app_uaa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UserMapper {
    UserOutputV2 getOutputFromEntity(UserEntity userEntity);
    UserEntity getEntityFromRequest(UserRequest signUpRequest);
    void updateEntityFromInput(@MappingTarget UserEntity userEntity,
                               ChangeInfoUserRequest changeInfoUserRequest);
}

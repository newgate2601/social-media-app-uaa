package com.example.social_media_app_uaa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeInfoUserRequest {
    private String fullName;
    private String birthdayString;
    private String gender;
    private String work;
    private String description;
    private String live;
    private String imageUrl;
}

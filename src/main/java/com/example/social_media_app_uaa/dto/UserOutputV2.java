package com.example.social_media_app_uaa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserOutputV2 {
    private Long id;
    private String fullName;
    private String imageUrl;
    private String imageBackground;
    private OffsetDateTime birthday;
    private String gender;
    private String work;
    private String description;
    private String live;
}

package com.example.social_media_app_uaa.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String fullName;
    @NotEmpty
    private OffsetDateTime birthday;
}

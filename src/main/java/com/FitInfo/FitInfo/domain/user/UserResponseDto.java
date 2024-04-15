package com.FitInfo.FitInfo.domain.user;

import lombok.Builder;

@Builder
public record UserResponseDto(
    Long id,
    String username,
    String email,
    String region
) {

  public static UserResponseDto createUserResponseDto(User user){

    return UserResponseDto.builder()
        .username(user.getUsername())
        .email(user.getEmail())
        .region(user.getRegion())
        .build();
  }
}


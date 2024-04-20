package com.FitInfo.FitInfo.domain.user;

public record UpdatedUserResponseDto(
    String Email,
    String password,
    String region
) {

}

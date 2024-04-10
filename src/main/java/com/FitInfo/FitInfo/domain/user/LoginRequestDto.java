package com.FitInfo.FitInfo.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequestDto(

    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,12}$", message = "아이디는 4~12 자, 영대소문자, 숫자만 가능합니다.")
    String username,

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 4, max= 15 , message = "비밀번호는 4~15 자여야 합니다.")
    String password
    ){

}
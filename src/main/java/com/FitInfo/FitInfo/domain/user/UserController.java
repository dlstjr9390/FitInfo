package com.FitInfo.FitInfo.domain.user;

import com.FitInfo.FitInfo.global.jwt.JwtUtil;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

  private final JwtUtil jwtUtil;

  private final UserService userService;

  @PostMapping("/signup")
  public String signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {

    userService.signup(signupRequestDto);

    return "회원가입 완료";
  }

  @GetMapping("/login")
  public String login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse res) {

    userService.login(loginRequestDto, res);

    return "로그인 완료";
  }
}

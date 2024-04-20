package com.FitInfo.FitInfo.domain.user;

import com.FitInfo.FitInfo.global.jwt.AuthUser;
import com.FitInfo.FitInfo.global.jwt.JwtUtil;
import com.FitInfo.FitInfo.global.response.CommonResponse;
import com.FitInfo.FitInfo.global.response.ResponseCode;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public ResponseEntity<CommonResponse<String>> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {

    userService.signup(signupRequestDto);

    return ResponseEntity
        .status(ResponseCode.SIGNUP.getHttpStatus())
        .body(CommonResponse.of(ResponseCode.SIGNUP, ""));
  }

  @GetMapping("/login")
  public ResponseEntity<CommonResponse<String>> login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse res) {

    userService.login(loginRequestDto, res);

    return ResponseEntity
        .status(ResponseCode.LOGIN.getHttpStatus())
        .body(CommonResponse.of(ResponseCode.LOGIN, ""));
  }

  @GetMapping("/{userId}")
  public ResponseEntity<CommonResponse<UserResponseDto>> getProfile(
      @PathVariable Long userId,
      @AuthUser User user
  ) {

    UserResponseDto userResponseDto = userService.getProfile(userId, user);

    return ResponseEntity
        .status(ResponseCode.GET_PROFILE.getHttpStatus())
        .body(CommonResponse.of(ResponseCode.GET_PROFILE, userResponseDto));
  }

  @PatchMapping("/{userId}")
  public ResponseEntity<CommonResponse<String>> updateUser(
      @PathVariable Long userId,
      UpdatedUserResponseDto updatedUserResponseDto,
      @AuthUser User user
  ) {

    userService.updateUser(userId, updatedUserResponseDto, user);

    return ResponseEntity
        .status(ResponseCode.OK.getHttpStatus())
        .body(CommonResponse.of(ResponseCode.OK, ""));
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<CommonResponse<String>> deleteUser(
      @PathVariable Long userId,
      @AuthUser User user
  ) {

    userService.deleteUser(userId, user);

    return ResponseEntity
        .status(ResponseCode.OK.getHttpStatus())
        .body(CommonResponse.of(ResponseCode.OK, ""));
  }

}

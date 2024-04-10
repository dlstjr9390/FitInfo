package com.FitInfo.FitInfo.domain.user;

import com.FitInfo.FitInfo.global.exception.BisException;
import com.FitInfo.FitInfo.global.exception.ErrorCode;
import com.FitInfo.FitInfo.global.jwt.JwtUtil;
import com.FitInfo.FitInfo.global.response.CommonResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

  private final JwtUtil jwtUtil;

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void signup(SignupRequestDto signupRequestDto) {

    checkConflictEmail(signupRequestDto.email());
    checkConflictUsername(signupRequestDto.username());

    String password = passwordEncoder.encode(signupRequestDto.password());

    User user = new User(signupRequestDto, password);

    userRepository.save(user);
  }

  public void login(LoginRequestDto loginRequestDto, HttpServletResponse res) {

    User user = userRepository.findByUsername(loginRequestDto.username()).orElseThrow(
        () -> new BisException(ErrorCode.NOT_FOUND_USER)
    );

    if(!passwordEncoder.matches(loginRequestDto.password(), user.getPassword())){

      throw new BisException(ErrorCode.WRONG_PASSWORD);

    }

    String token = jwtUtil.createAccessToken(loginRequestDto.username());
    jwtUtil.addJwtToCookie("accessToken", token, res);

  }

  public void checkConflictEmail(String email) {

    if(userRepository.existsUserByEmail(email)){

      throw new BisException(ErrorCode.EXIST_EMAIL);
    }
  }

  public void checkConflictUsername(String username) {

    if(userRepository.existsUserByUsername(username)) {

      throw new BisException(ErrorCode.EXIST_USERNAME);
    }
  }

}

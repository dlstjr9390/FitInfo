package com.FitInfo.FitInfo.domain.user;

import com.FitInfo.FitInfo.global.exception.BisException;
import com.FitInfo.FitInfo.global.exception.ErrorCode;
import com.FitInfo.FitInfo.global.jwt.JwtUtil;
import com.FitInfo.FitInfo.global.response.CommonResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
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

    if(!user.isActive()){

      throw new BisException(ErrorCode.DELETED_USER);
    }

    String token = jwtUtil.createAccessToken(loginRequestDto.username());
    jwtUtil.addJwtToCookie("accessToken", token, res);

  }

  public UserResponseDto getProfile(Long userId, User loginedUser) {

    User user = getUser(userId);

    if(user.getId() != loginedUser.getId()){
      throw new BisException(ErrorCode.YOUR_NOT_COME_IN);
    }

    return UserResponseDto.createUserResponseDto(user);
  }

  @Transactional
  public void updateUser(
      Long userId,
      UpdatedUserResponseDto updatedUserResponseDto,
      User loginedUser
  ) {

    User user = getUser(userId);

    if(user.getId() != loginedUser.getId()){
      throw new BisException(ErrorCode.YOUR_NOT_COME_IN);
    }

    user.updateUser(updatedUserResponseDto);
  }

  @Transactional
  public void deleteUser(Long userId, User loginedUser) {

    User user = getUser(userId);

    if(user.getId() != loginedUser.getId()){
      throw new BisException(ErrorCode.YOUR_NOT_COME_IN);
    }

    user.inActiveUser();
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

  public User getUser(Long userId) {

    User user = userRepository.findById(userId).orElseThrow(
        () -> new BisException(ErrorCode.NOT_FOUND_USER)
    );

    return user;
  }


}

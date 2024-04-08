package com.FitInfo.FitInfo.domain.user;

import com.FitInfo.FitInfo.global.exception.BisException;
import com.FitInfo.FitInfo.global.exception.ErrorCode;
import com.FitInfo.FitInfo.global.response.CommonResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

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

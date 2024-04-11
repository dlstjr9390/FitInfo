package com.FitInfo.FitInfo.domain.user;

import com.FitInfo.FitInfo.global.timestamp.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class User extends Timestamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String region;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private boolean isActive;

  @Column(nullable = false)
  private String role;

  @Column
  private Long kakaoId;

  public User(SignupRequestDto signupRequestDto, String password) {

    this.username = signupRequestDto.username();
    this.password = password;
    this.region = signupRequestDto.region();
    this.email = signupRequestDto.email();
    this.isActive = true;
    this.role = "User";
  }

  public User(String username, String password, String email, Long kakaoId) {

    this.username = username;
    this.password = password;
    this.email = email;
    this.kakaoId = kakaoId;
  }

  public User kakaoIdUpdate(Long kakaoId) {
    this.kakaoId = kakaoId;
    return this;
  }

}

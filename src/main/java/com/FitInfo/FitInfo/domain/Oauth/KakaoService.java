package com.FitInfo.FitInfo.domain.Oauth;

import com.FitInfo.FitInfo.domain.user.User;
import com.FitInfo.FitInfo.domain.user.UserRepository;
import com.FitInfo.FitInfo.global.jwt.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j(topic = "Kakao Login")
@Service
@RequiredArgsConstructor
public class KakaoService {

  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  private final RestTemplate restTemplate;

  private JwtUtil jwtUtil;

  public String kakaoLogin(String code) throws JsonProcessingException {

    String accessToken = getToken(code);

    KakaoUserInfoDto kaKaoUserInfoDto = getKakaoUserInfo(accessToken);

    User kakaoUser = registerKakaoUserIfNeeded(kaKaoUserInfoDto);

    String createdToken = jwtUtil.createAccessToken(kakaoUser.getUsername(), kakaoUser.getRole());

    return createdToken;
  }

  private String getToken(String code) throws JsonProcessingException {

    URI uri = UriComponentsBuilder
        .fromUriString("https://kauth.kakao.com")
        .path("/oauth/token")
        .encode()
        .build()
        .toUri();

    HttpHeaders headers = new HttpHeaders();
    headers.add(
        "Content-type",
        "application/x-www-form-urlencoded;charset=utf-8"
    );

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "authorization_code");
    body.add("client_id", "d21f44115769e401fe31f10ffcf912ce");
    body.add("redirect_uri", "http://localhost:8080/api/user/kakao/callback");
    body.add("code", code);

    RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
        .post(uri)
        .headers(headers)
        .body(body);

    ResponseEntity<String> response = restTemplate.exchange(
        requestEntity,
        String.class
    );

    JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());

    return jsonNode.get("access_token").asText();
  }

  private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {log.info("accessToken :" + accessToken);

    URI uri = UriComponentsBuilder
        .fromUriString("https://kapi.kakao.com")
        .path("/v2/user/me")
        .encode()
        .build()
        .toUri();

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + accessToken);
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
        .post(uri)
        .headers(headers)
        .body(new LinkedMultiValueMap<>());

    ResponseEntity<String> response = restTemplate.exchange(
        requestEntity,
        String.class
    );

    JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
    Long id = jsonNode.get("id").asLong();
    String nickname = jsonNode.get("properties").get("nickname").asText();
    String email = jsonNode.get("kakao_account").get("email").asText();

    log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);

    return new KakaoUserInfoDto(id, nickname, email);
  }

  private User registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {

    // DB 에 중복된 Kakao Id 가 있는지 확인
    Long kakaoId = kakaoUserInfo.getId();
    User kakaoUser = userRepository.findByKakaoId(kakaoId).orElse(null);

    if (kakaoUser == null) {
      // 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인
      String kakaoEmail = kakaoUserInfo.getEmail();
      User sameEmailUser = userRepository.findByEmail(kakaoEmail).orElse(null);
      if (sameEmailUser != null) {
        kakaoUser = sameEmailUser;
        // 기존 회원정보에 카카오 Id 추가
        kakaoUser = kakaoUser.kakaoIdUpdate(kakaoId);
      } else {
        // 신규 회원가입
        // password: random UUID
        String password = UUID.randomUUID().toString();
        String encodedPassword = passwordEncoder.encode(password);

        // email: kakao email
        String email = kakaoUserInfo.getEmail();

        kakaoUser = new User(kakaoUserInfo.getNickname(), encodedPassword, email, kakaoId);
      }

      userRepository.save(kakaoUser);
    }

    return kakaoUser;
  }
}

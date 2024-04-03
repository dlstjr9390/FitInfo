package com.FitInfo.FitInfo.glabal.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private static final String AUTHORIZATION_KEY = "Bearer";

  private final String REFRESH_TOKEN = "refresh";

  private final String ACCESS_TOKEN = "access";

  private final long ACCESS_TOKEN_TIME = 24 * 60 * 60 * 1000L; // 1일

  private final long REFRESH_TOKEN_TIME = 30 * 24 * 60 * 60 * 1000L; // 1달

  @Value("${jwt.secret}")
  private String secretKey;

  private Key key;

  private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

}

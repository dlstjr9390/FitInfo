package com.FitInfo.FitInfo.global.jwt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
@Retention(RetentionPolicy.RUNTIME)
// 타겟은 파라미터에만
@Target(ElementType.PARAMETER)

// 익명 사용자인 경우에는 null로, 익명 사용자가 아닌 경우에는 실제 CustomuUserDetails의 User 객체로
// Principal 을 동적으로 꺼내기 위해 @AuthUser 생성
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member")
public @interface AuthUser {

}
/**
 * 시큐리티 완성하시면 주석 풀고 사용하시면 됩니다.
 *
 * @AuthenticationPrincipal  이거 대신
 *
 * @AuthUser Member member 로 바로 user 가져올 수 있습니다.


 **/

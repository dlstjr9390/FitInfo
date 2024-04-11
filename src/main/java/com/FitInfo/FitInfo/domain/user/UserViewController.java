package com.FitInfo.FitInfo.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user")
public class UserViewController {

  @GetMapping("/signup-page")
  public String SignupPage() {

    return "signup";
  }
}

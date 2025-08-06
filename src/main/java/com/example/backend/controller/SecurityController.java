package com.example.backend.controller;

import com.example.backend.dto.SubscriberDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/subscriber")
@RequiredArgsConstructor
@Log4j2
public class SecurityController {

    private final AuthenticationManager authenticationManager;

    @Operation(summary = "로그인 요청", description = "이메일(username)과 비밀번호(password)를 통해 로그인 후 JWT 토큰과 사용자 정보를 반환")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("----------------------------------------------------------------");
            log.info(authentication);
            log.info("----------------------------------------------------------------");
            SubscriberDTO subscriberDTO = (SubscriberDTO) authentication.getPrincipal();

            //JWT Claims 생성 위치
            Map<String, Object> claims = new HashMap<>(subscriberDTO.getClaims());
            claims.put("accessToken","");
            claims.put("refreshToken","");
            return ResponseEntity.ok(claims);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        }
    }
}

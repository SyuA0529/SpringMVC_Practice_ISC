package com.logicserver.service;

import com.logicserver.dto.SignRequest;
import com.logicserver.dto.SignResponse;
import com.logicserver.member.Authority;
import com.logicserver.member.Member;
import com.logicserver.member.MemberRepository;
import com.logicserver.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SignService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public SignResponse login(SignRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("wrong account information"));
        if(!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("wrong account information");
        }

        return SignResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .roles(member.getRoles())
                .token(jwtTokenProvider.createToken(member.getEmail(), member.getRoles()))
                .build();
    }

    public boolean register(SignRequest request) throws Exception {
        try {
            log.info("email: {}", request.getEmail());
            log.info("nickname: {}", request.getNickname());
            log.info("password: {}", request.getPassword());
            Member member = Member.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .nickname(request.getNickname())
                    .build();
            member.setRoles(Collections.singletonList(
                    Authority.builder().name("ROLE_USER").build()
            ));
            memberRepository.save(member);
        } catch(Exception e) {
            throw new Exception("wrong request");
        }
        return true;
    }

    public SignResponse getMember(String email) throws Exception {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("can't find acccount"));
        return new SignResponse(member);
    }
}
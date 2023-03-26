package com.logicserver.dto;

import com.logicserver.member.Authority;
import com.logicserver.member.Member;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignResponse {
    private Long id;
    private String email;
    private String nickname;
    private List<Authority> roles = new ArrayList<>();
    private String token;

    public SignResponse(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.roles = member.getRoles();
    }
}


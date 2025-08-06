package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class SubscriberDTO extends User {
    private String email;
    private String pwd;
    private String nickname;
    private boolean social;
    private List<String> roleNames = new ArrayList<>();
    public SubscriberDTO(String email, String pwd, String nickname, boolean social, List<String> roleNames) {
        super(email, pwd, roleNames.stream().map(str -> new SimpleGrantedAuthority("ROLE_" + str))
                .collect(Collectors.toList()));
        this.email = email;
        this.pwd = pwd;
        this.nickname = nickname;
        this.social = social;
        this.roleNames = roleNames;
    }
    public Map<String, Object> getClaims() {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("pwd", pwd);
        map.put("nickname", nickname);
        map.put("social", social);
        map.put("roleNames", roleNames);
        return map;
    }
}

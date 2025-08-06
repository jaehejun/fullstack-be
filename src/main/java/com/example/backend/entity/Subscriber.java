package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subscriber")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "subscriberRoleList")
public class Subscriber {
    @Id
    private String email;
    private String pwd;
    private String nickname;
    private boolean social;
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<SubscriberRole> subscriberRoleList = new ArrayList<>();

    public void addRole(SubscriberRole subscriberRole) { subscriberRoleList.add(subscriberRole); }
    public void clearRoles() { subscriberRoleList.clear(); }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public void setPwd(String pwd) { this.pwd = pwd; }
    public void setSocial(boolean social) { this.social = social; }
}

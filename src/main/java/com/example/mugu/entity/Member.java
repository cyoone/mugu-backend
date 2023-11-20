package com.example.mugu.entity;

import com.example.mugu.constant.Role;
import com.example.mugu.dto.MemberFormDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member extends BaseEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id;

    @Column(unique = true)
    private String phone;

    private String password;

    private String nickname;

    private Integer manner;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "created_at", insertable = false, updatable = false)
    private String created_at;
    private String updated_at;

    private String city;
    private String county;
    private String town;

    public static Member createMember(MemberFormDTO memberFormDTO, BCryptPasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setPhone(memberFormDTO.getPhone());
        member.setNickname(memberFormDTO.getNickname());
        member.setManner(300);
        String password = passwordEncoder.encode(memberFormDTO.getPassword());
        member.setPassword(password);
        member.setRole(Role.USER);
        member.setCreated_at(memberFormDTO.getCreatedAt());
        member.setUpdated_at(memberFormDTO.getUpdated_at());
        member.setCity(memberFormDTO.getCity());
        member.setCounty(memberFormDTO.getCounty());
        member.setTown(memberFormDTO.getTown());

        return member;
    }
}

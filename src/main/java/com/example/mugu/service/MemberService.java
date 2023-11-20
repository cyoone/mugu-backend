package com.example.mugu.service;

import com.example.mugu.entity.Member;
import com.example.mugu.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${coolsms.api.key}")
    private String apiKey;
    @Value("${coolsms.api.secret}")
    private String apiSecret;

    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByPhone(member.getPhone());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    public Member login(String phone, String password) {
        Member member = memberRepository.findByPhone(phone);
        if (member == null) {
            throw new IllegalStateException("회원가입된 정보가 없습니다.");
        }
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return member;
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Member member = memberRepository.findByPhone(phone);
        if (member == null) {
            throw new UsernameNotFoundException(phone);
        }
        return User.builder()
                .username(member.getPhone())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    public void certifiedPhoneNumber(String memberPhone, int randomNumber) {
        Message coolsms = new Message(apiKey, apiSecret);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", memberPhone);
        params.put("from", "01065639503");

        params.put("type", "SMS");
        params.put("text", "[Mugu] 인증번호는" + "[" + randomNumber + "]" + "입니다.");
        params.put("app_version", "test app 2.2");

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
    }
}
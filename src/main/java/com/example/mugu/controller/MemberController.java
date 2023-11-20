package com.example.mugu.controller;

import com.example.mugu.dto.MemberFormDTO;
import com.example.mugu.entity.Member;
import com.example.mugu.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AddressController addressController;

    @GetMapping(value = "/new")
    public String memberForm(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("memberFormDTO", new MemberFormDTO());

        // 도시 정보를 가져와서 모델에 추가
        List<String> cities = addressController.getCities();
        model.addAttribute("cities", cities);

        return "member/signUp";
    }

    @PostMapping(value = "/new")
    public String newMember(@Valid MemberFormDTO memberFormDTO, BindingResult bindingResult, Model model,
                            @RequestParam String city,
                            @RequestParam String county,
                            @RequestParam String town){

        if (bindingResult.hasErrors()) {
            return "member/signUp";
        }

        try {
            Member member = Member.createMember(memberFormDTO, passwordEncoder);
            member.setCity(city); // 도시 정보를 설정
            member.setCounty(county); // 군/구 정보를 설정
            member.setTown(town); // 읍/면/동 정보를 설정
            Member savedMember = memberService.saveMember(member);

        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/signUp";
        }
        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String loginMember() {
        return "member/signIn";
    }

    @PostMapping(value = "/login")
    public String loginMember(
            @RequestParam("phone") String phone,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {
        try {
            Member member = memberService.login(phone, password);
            session.setAttribute("member", member);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/signIn";
        }
    }

    @RequestMapping(value = "/phoneCheck", method = RequestMethod.GET)
    @ResponseBody
    public String sendSMS(@RequestParam("phone") String memberPhone) {
        int randomNumber = (int)(Math.random()* (9999 - 1000 +1)) + 1000;

        memberService.certifiedPhoneNumber(memberPhone, randomNumber);

        return Integer.toString(randomNumber);
    }

}
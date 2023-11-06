package com.example.mugu.controller;

import com.example.mugu.dto.UserDTO;
import com.example.mugu.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    // 생성자 주입
    private final UserService userService;

    // 회원가입 페이지 출력 요청
    @GetMapping("/user/signup")
    public String signupForm() {
        return "signup";
    }

    @PostMapping("/user/signup")
    public String signup(@ModelAttribute UserDTO userDTO) {
        System.out.println("UserController.signup");
        System.out.println("userDTO = " + userDTO);
        userService.signup(userDTO);
        return "signin";
    }

    @GetMapping("/user/signin")
    public String loginForm() {
        return "signin";
    }

    @PostMapping("/user/signin")
    public String signin(@ModelAttribute UserDTO userDTO, HttpSession session) {
        UserDTO signinResult = userService.signin(userDTO);
        if (signinResult != null) {
            // login 성공
            session.setAttribute("signinPhone", signinResult.getPhone());
            return "main";
        } else {
            // login 실패
            return "signin";
        }
    }

    @GetMapping("/user/")
    public String findAll(Model model) {
        List<UserDTO> userDTOList = userService.findAll();
        // 어떠한 html로 가져갈 데이터가 있다면 model 사용
        model.addAttribute("userList", userDTOList);
        return "list";
    }

    @GetMapping("/user/{User_id}")
    public String findById(@PathVariable Long User_id, Model model) {
        UserDTO userDTO = userService.findById(User_id);
        model.addAttribute("user", userDTO);
        return "detail";
    }

    @GetMapping("/user/update")
    public String updateForm(HttpSession session, Model model) {
        String myPhone = (String) session.getAttribute("phone");
        UserDTO userDTO =  userService.updateForm(myPhone);
        model.addAttribute("updateUser", userDTO);
        return "update";
    }

    @PostMapping("/user/update")
    public String update(@ModelAttribute UserDTO userDTO) {
        userService.update(userDTO);
        return "redirect:/user/" + userDTO.getUser_id();
    }

    @GetMapping("/user/delete/{User_id}")
    public String deleteById(@PathVariable Long User_id) {
        userService.deleteById(User_id);
        return "redirect:/user/";
    }

    @GetMapping("/user/signout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    @PostMapping("/user/phone-check")
    public @ResponseBody String phoneCheck(@RequestParam("phone") String phone) {
        System.out.println("phone = " + phone);
        String checkResult = userService.phoneCheck(phone);
        return checkResult;
        /*if (checkResult != null) {
            return "ok";
        } else {
            return "no";
        }*/
    }

}
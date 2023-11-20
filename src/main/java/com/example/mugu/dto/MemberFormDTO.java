package com.example.mugu.dto;

import com.example.mugu.entity.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class MemberFormDTO extends BaseEntity {

    @NotBlank(message = "핸드폰번호는 필수 입력 값입니다.")
    @Length(max = 11, message = "핸드폰번호는 -없이 숫자만 입력해주세요")
    private String phone;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Length(min = 2, max = 10, message = "닉네임은 2자 이상, 10자 이하로 입력해주세요")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min = 4, max = 30, message = "비밀번호는 4자 이상, 30자 이하로 입력해주세요")
    private String password;

    private boolean verified = false;

    private String created_at;
    private String updated_at;

    private String city;
    private String county;
    private String town;
}

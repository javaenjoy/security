package com.example.securityapp.dto;


import lombok.*;
import javax.validation.constraints.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class LoginForm {
    //null, "", " " 모두 허용하지 않는다.
    @NotBlank(message = "이메일을 정확히 입력하세요.")
    @Email
    private String email;

    @NotBlank(message = "비밀번호를 정확히 입력하세요.")
    private String password;

}

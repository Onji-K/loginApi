package com.onjee.loginApi.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JoinUserRequestDto {

    @NotBlank
    private String username;
    @Email
    private String email;
    @NotBlank
    private String pw;
}

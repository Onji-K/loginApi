package com.onjee.loginApi.controller;


import com.onjee.loginApi.domain.common.CommonApiResponse;
import com.onjee.loginApi.domain.member.MemberEntity;
import com.onjee.loginApi.dto.JoinUserRequestDto;
import com.onjee.loginApi.exception.JoinWithOccupiedEmailException;
import com.onjee.loginApi.exception.JoinWithOccupiedUsernameException;
import com.onjee.loginApi.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class LoginController {


    private final LoginService loginService;

    @PostMapping("")
    public ResponseEntity join(@RequestBody @Valid JoinUserRequestDto joinUserRequestDto){
        MemberEntity joinedMember = null;
        CommonApiResponse result = null;
        try {
            joinedMember = loginService.join(joinUserRequestDto);
        } catch (JoinWithOccupiedUsernameException | JoinWithOccupiedEmailException e) {
            //중복된 아이디 or 이메일
            result = CommonApiResponse.builder()
                    .code("400")
                    .success(false)
                    .msg(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        result = CommonApiResponse.builder()
                .code("200")
                .success(true)
                .msg("You have successfully registered as a member.")
                .data(joinedMember.getUsername())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

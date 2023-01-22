package com.onjee.loginApi.service;

import com.onjee.loginApi.domain.member.MemberEntity;
import com.onjee.loginApi.dto.JoinUserRequestDto;
import com.onjee.loginApi.exception.JoinWithOccupiedEmailException;
import com.onjee.loginApi.exception.JoinWithOccupiedUsernameException;

public interface LoginService{
    public MemberEntity join(JoinUserRequestDto joinRequestData) throws JoinWithOccupiedUsernameException, JoinWithOccupiedEmailException;
}

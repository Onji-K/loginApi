package com.onjee.loginApi.domain.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommonApiResponse<T> {

    private boolean success;
    private String code;
    private String msg;
    private T data;

}

package com.onjee.loginApi.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonErrorResponse<T> {
    private T error;
}

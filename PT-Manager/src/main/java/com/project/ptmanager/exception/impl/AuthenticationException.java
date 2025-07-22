package com.project.ptmanager.exception.impl;

import com.project.ptmanager.exception.BaseException;
import org.springframework.http.HttpStatus;

public class AuthenticationException extends BaseException {
    @Override
    public int getStatusCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }
    @Override
    public String getMessage() {
        return "인증에 실패하였습니다.";
    }
}

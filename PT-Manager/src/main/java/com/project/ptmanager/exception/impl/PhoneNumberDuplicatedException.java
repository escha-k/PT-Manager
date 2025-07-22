package com.project.ptmanager.exception.impl;

import com.project.ptmanager.exception.BaseException;
import org.springframework.http.HttpStatus;

public class PhoneNumberDuplicatedException extends BaseException {
    @Override
    public int getStatusCode() {
        return HttpStatus.CONFLICT.value();
    }
    @Override
    public String getMessage() {
        return "이미 가입된 전화번호입니다.";
    }
}

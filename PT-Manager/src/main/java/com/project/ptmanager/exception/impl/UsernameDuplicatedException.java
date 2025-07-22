package com.project.ptmanager.exception.impl;

import com.project.ptmanager.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UsernameDuplicatedException extends BaseException {
    @Override
    public int getStatusCode() {
        return HttpStatus.CONFLICT.value();
    }
    @Override
    public String getMessage() {
        return "중복된 ID 입니다.";
    }
}

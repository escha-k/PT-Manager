package com.project.ptmanager.exception.impl;

import com.project.ptmanager.exception.BaseException;
import org.springframework.http.HttpStatus;

public class StatisticsNotFoundException extends BaseException {
    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
    @Override
    public String getMessage() {
        return "통계 정보를 찾을 수 없습니다.";
    }
}

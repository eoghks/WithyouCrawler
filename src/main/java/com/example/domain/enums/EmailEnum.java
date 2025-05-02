package com.example.domain.enums;

//수신 받을 이메일 작성
public enum EmailEnum {
    EOGHKS("zhfldk7316@naver.com");

    private final String address;

    EmailEnum(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}

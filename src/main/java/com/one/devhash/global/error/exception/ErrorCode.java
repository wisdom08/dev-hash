package com.one.devhash.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // 공통
    INVALID_INPUT_VALUE(400, " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405,  " Invalid Input Value"),
    ENTITY_NOT_FOUND(404,  " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "Server Error"),
    INVALID_TYPE_VALUE(400, " Invalid Type Value"),

    // 유저
    HANDLE_ACCESS_DENIED(403, "로그인이 필요합니다."),
    INVALID_INPUT_USERNAME(400, "닉네임을 3자 이상 입력하세요"),
    NOTEQUAL_INPUT_PASSWORD(400,  "비밀번호가 일치하지 않습니다"),
    INVALID_PASSWORD(400,  "비밀번호를 8자 이상 입력하세요"),
    INVALID_USERNAME(400,  "알파벳 대소문자와 숫자로만 입력하세요"),
    NOT_AUTHORIZED(403, "작성자만 수정 및 삭제를 할 수 있습니다."),
    USERNAME_DUPLICATION(400, "이미 등록된 아이디입니다."),
    LOGIN_INPUT_INVALID(400, "로그인 정보를 다시 확인해주세요."),
    NOTFOUND_USER(404,  "해당 이름의 유저가 존재하지 않습니다."),
    NOT_VALID_REFRESH_TOKEN(400, "유효한 리프레시 토큰이 아닙니다."),
    Expired_JWT_EXCEPTION(400, "리프레시토큰이 만료되었습니다."),
    NOT_EQUAL_REFRESH_TOKEN(400, "해당 리프레시 토큰은 보관된 리프레시 토큰의 정보와 다릅니다."),

    // 게시글
    NOTFOUND_PRODUCT(404, "해당 상품이 존재하지 않습니다."),
    CONVERTING_FAILED(400, "파일 변환에 실패했습니다."),

    // 채팅
    INVALID_REQUEST_FOR_CHAT(400, "다른 유저의 상품글에만 채팅방을 만들 수 있습니다."),
    DUPLICATED_REQUEST_FOR_CHAT(400, "채팅방이 이미 있습니다."),
    NOTFOUND_CATTING_ROOM(404,  "채팅방이 존재하지 않습니다."),
            ;
    private final String message;
    private final int status;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
    public int getStatus() {
        return status;
    }
}

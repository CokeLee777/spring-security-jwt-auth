package io.github.cokelee777.springsecurityjwtauth.utils;

public interface DefaultHttpMessage {
    String OK = "요청에 성공하였습니다.";
    String UNAUTHORIZED = "인증이 필요한 요청입니다.";
    String BAD_REQUEST = "잘못된 요청입니다.";
    String FORBIDDEN = "승인이 필요한 페이지 요청입니다.";
    String NOT_FOUND = "페이지 요청이 존재하지 않습니다.";
    String METHOD_NOT_ALLOWED = "허용되지 않은 HTTP 메서드 입니다.";
    String CONFLICT = "중복된 요청입니다.";
    String UNSUPPORTED_MEDIA_TYPE = "허용되지 않은 컨텐츠 타입입니다.";
    String INTERNAL_SERVER_ERROR = "서버 내부 오류";
}

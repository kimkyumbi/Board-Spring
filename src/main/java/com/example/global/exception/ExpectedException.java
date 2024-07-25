package com.example.global.exception;

import org.springframework.http.HttpStatus;

/**
 * RuntimeException을 상속받는 커스텀 Exception
 * 특정 HTTP 상태 코드를 포함하는 예외를 생성할 때 사용
 */
public class ExpectedException extends RuntimeException {
    // 변경할 수 없는 HttpStatus 코드 필드
    private final HttpStatus statusCode;

    /**
     * HttpStatus 코드를 반환하는 Getter 메서드
     * @return HttpStatus 상태 코드
     */
    public HttpStatus getStatusCode() {
        return statusCode;
    }

    /**
     * 메시지와 HttpStatus 코드를 받아서 커스텀 예외를 생성하는 생성자
     * @param message 예외 메시지
     * @param statusCode HTTP 상태 코드
     */
    public ExpectedException(String message, HttpStatus statusCode) {
        super(message);  // RuntimeException의 생성자를 호출하여 메시지 설정
        this.statusCode = statusCode;  // HttpStatus 코드 설정
    }

    /**
     * HttpStatus 코드만을 받아서 커스텀 예외를 생성하는 생성자
     * 상태 코드의 설명을 메시지로 설정
     * @param statusCode HTTP 상태 코드
     */
    public ExpectedException(HttpStatus statusCode) {
        this(statusCode.getReasonPhrase(), statusCode);  // 위의 생성자를 호출하여 메시지를 상태 코드의 설명으로 설정
    }

    /**
     * 스택 트레이스를 채우지 않는 오버라이드된 메서드
     * 성능 최적화를 위해 스택 트레이스를 생략
     * @return 현재 예외 객체
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;  // 스택 트레이스를 채우지 않고 현재 예외 객체 반환
    }
}

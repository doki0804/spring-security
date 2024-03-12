package com.example.springsecurity.security.enums;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	
	/* 400 BAD_REQUEST : 잘못된 요청 */
	MISMATCH_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
	TOKEN_TYPE(HttpStatus.BAD_REQUEST, "토큰 타입이 올바르지 않습니다."),
	UNAVAILABLE_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "사용할 수 없는 토큰 입니다."),
	EXISTING_BUSINESS_NUMBER(HttpStatus.BAD_REQUEST, "이미 존재하는 사업자번호입니다."),
	EXISTING_USER_ID(HttpStatus.BAD_REQUEST, "사용할 수 없는 아이디입니다."),
	VALIDATION_ERROR_EMPTY_FIELD(HttpStatus.BAD_REQUEST, "필수 항목이 누락되었습니다."),
	VALIDATION_ERROR_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "형식이 올바르지 않습니다."),
	DUPLICATE_LOCATION_CD(HttpStatus.BAD_REQUEST, "이미 사용중인 이름입니다."),
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

	/* 401 Unauthorized : 권한 없음 */
	UNAUTHORIZED_RESOURCE(HttpStatus.UNAUTHORIZED, "접근권한이 없습니다."),

	/* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
	RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource를 찾을 수 없습니다."),
	EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이메일을 찾을 수 없습니다."),
	REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "리프레쉬 토큰을 찾을 수 없습니다."),
	NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 공지사항입니다. 다시 시도해주세요"),
	QNA_NOT_FOUND(HttpStatus.NOT_FOUND, "QnA 찾을 수 없습니다."),

	/* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
	DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이메일이 이미 존재합니다."),
	DELETED_EMAIL(HttpStatus.CONFLICT, "이미 삭제된 이메일 입니다."),



	/* 500 Internal Server Error : 서버 에러 */
	INSERT_ITEM(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러 발생(insert)"),
	UPDATE_ITEM(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러 발생(update)"),
	DELETE_ITEM(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러 발생(delete)"),
	ITEM_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러 발생(file not found)");

	private final HttpStatus httpStatus;
	private final String message;
	
}

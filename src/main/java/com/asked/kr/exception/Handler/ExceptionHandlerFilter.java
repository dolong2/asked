package com.asked.kr.exception.Handler;

import com.asked.kr.exception.ErrorCode;
import com.asked.kr.exception.exceptions.AccessTokenExpiredException;
import com.asked.kr.exception.exceptions.InvalidTokenException;
import com.asked.kr.exception.exceptions.NoMemberException;
import com.asked.kr.exception.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    /**
     * filter에서 발생한 Exception을 catch한 후 사용자에게 예외 Response를 전달합니다.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param filterChain filterChain
     * @author 정시원
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        }catch (InvalidTokenException e){
            responseErrorMessage(response, e.getErrorCode());
        }catch (AccessTokenExpiredException e){
            responseErrorMessage(response, e.getErrorCode());
        }catch (NoMemberException e){
            responseErrorMessage(response, e.getErrorCode());
        } catch (Exception e){
            log.error("알 수 없는 에러 발생", e);
            responseErrorMessage(response, ErrorCode.UNKNOWN_ERROR);
        }
    }

    private void responseErrorMessage(HttpServletResponse response, ErrorCode errorCode) {
        // content type, status code 세팅
        response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        response.setStatus(errorCode.getStatus());

        try {
            ErrorResponse errorResponse = new ErrorResponse(errorCode);
            String errorResponseEntityToJson = objectMapper.writeValueAsString(errorResponse);

            response.getWriter().write(errorResponseEntityToJson); // filter 단에서 client에 json를 보낸다.
        } catch (IOException e) {
            log.error("Filter에서 Error Response Json변환 실패", e);
            throw new RuntimeException(e); // 알 수 없는 에러를 위해 일단 RuntimeException을 발생시킴
        }
    }
}

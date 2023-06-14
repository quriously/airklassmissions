package com.quriously.signup.application.controller

import com.quriously.signup.application.contract.ApiError
import com.quriously.signup.domain.contract.exception.DomainException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerExceptionAdviser {
    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ApiError> {
        val errors = e.bindingResult.fieldErrors.map{ error ->
            error.field to error.defaultMessage
        }.toMap()
        return ResponseEntity(
            ApiError(
                code = "account.request.invalid",
                message = "입력하신 데이터가 올바르지 않습니다.",
                data = errors,
            ),
            HttpStatus.BAD_REQUEST,
        )
    }

    @ExceptionHandler(value = [DomainException::class])
    fun handleDomainException(e: DomainException): ResponseEntity<ApiError> {
        return ResponseEntity(
            ApiError(
                code = e.code,
                message = e.message,
                data = e.data,
            ),
            HttpStatus.BAD_REQUEST,
        )
    }

    @ExceptionHandler(value = [HttpMessageNotReadableException::class])
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<ApiError> {
        return ResponseEntity(
            ApiError(
                code = "account.request.invalid",
                message = "입력하신 데이터가 올바르지 않습니다.",
                data = mapOf(),
            ),
            HttpStatus.BAD_REQUEST,
        )
    }
}
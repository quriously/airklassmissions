package com.quriously.signup.application.dto.request

import com.fasterxml.jackson.annotation.JsonFormat
import com.quriously.signup.domain.dto.request.AccountRegisterCommand
import com.quriously.signup.domain.entity.AccountTermType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class RegisterRequest(
    @field:Email(message = "이메일 형식이 올바르지 않습니다.")
    val email: String,
    val name: String,
    val verifyId: Long,
    val password: String,
    val passwordConfirm: String,
    val terms: List<AccountTermType>,
    @field:DateTimeFormat(pattern = "yyyy-MM-dd")
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @field:Schema(format = "date", example = "2023-06-14")
    val birthday: LocalDate,
){
    fun toCommand(): AccountRegisterCommand {
        return AccountRegisterCommand(
            email = email,
            name = name,
            accountVerifyId = verifyId,
            password = password,
            passwordConfirm = passwordConfirm,
            terms = terms,
            birthday = birthday,
        )
    }
}
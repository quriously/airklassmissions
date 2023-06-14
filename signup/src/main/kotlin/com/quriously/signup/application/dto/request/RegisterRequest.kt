package com.quriously.signup.application.dto.request

import com.quriously.signup.domain.dto.request.AccountRegisterCommand
import com.quriously.signup.domain.entity.AccountTermType
import jakarta.validation.constraints.Email
import org.springframework.format.annotation.DateTimeFormat
import java.util.*

data class RegisterRequest(
    @field:Email(message = "이메일 형식이 올바르지 않습니다.")
    val email: String,
    val verifyId: Long,
    val password: String,
    val passwordConfirm: String,
    val terms: List<AccountTermType>,
    @field:DateTimeFormat(pattern = "yyyy-MM-dd")
    val birthday: Date,
){
    fun toCommand(): AccountRegisterCommand {
        return AccountRegisterCommand(
            email = email,
            accountVerifyId = verifyId,
            password = password,
            passwordConfirm = passwordConfirm,
            terms = terms,
            birthday = birthday,
        )
    }
}
package com.quriously.signup.application.controller

import com.quriously.signup.application.dto.request.CodeRequest
import com.quriously.signup.application.dto.request.EmailRequest
import com.quriously.signup.application.dto.request.RegisterRequest
import com.quriously.signup.application.dto.response.AccountVerifyResponse
import com.quriously.signup.application.dto.response.EmailCodeResponse
import com.quriously.signup.domain.service.AccountService
import com.quriously.signup.domain.service.AccountVerifyService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/account")
class AccountController(
    private val accountVerifyService: AccountVerifyService,
    private val accountService: AccountService,
) {
    @PostMapping("/send")
    fun sendVerifyEmail(
        @RequestBody @Valid request: EmailRequest,
    ): EmailCodeResponse {
        return EmailCodeResponse(code = accountVerifyService.sendCode(request.email))
    }

    @PostMapping("/verify")
    fun verifyEmail(
        @RequestBody @Valid request: CodeRequest,
    ): AccountVerifyResponse {
        return AccountVerifyResponse(accountVerifyService.verifyCode(request.email, request.code))
    }

    @PostMapping("/register")
    fun registerAccount(
        @RequestBody @Valid request: RegisterRequest,
    ) {
        accountService.createAccount(request.toCommand())
    }
}
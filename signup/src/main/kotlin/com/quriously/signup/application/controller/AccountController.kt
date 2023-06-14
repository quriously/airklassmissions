package com.quriously.signup.application.controller

import com.quriously.signup.application.dto.request.CodeRequest
import com.quriously.signup.application.dto.request.EmailRequest
import com.quriously.signup.application.dto.request.LoginRequest
import com.quriously.signup.application.dto.request.RegisterRequest
import com.quriously.signup.application.dto.response.AccountResponse
import com.quriously.signup.application.dto.response.AccountVerifyResponse
import com.quriously.signup.application.dto.response.EmailCodeResponse
import com.quriously.signup.application.security.login.AuthToken
import com.quriously.signup.application.security.login.TokenProvider
import com.quriously.signup.domain.exception.PermissionDenyException
import com.quriously.signup.domain.service.AccountService
import com.quriously.signup.domain.service.AccountVerifyService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/account")
class AccountController(
    private val accountVerifyService: AccountVerifyService,
    private val accountService: AccountService,
    private val tokenProvider: TokenProvider,
) {
    @PostMapping("/send")
    @Operation(
        summary = "이메일 인증 코드를 보내는 API",
    )

    fun sendVerifyEmail(
        @RequestBody @Valid request: EmailRequest,
    ): EmailCodeResponse {
        return EmailCodeResponse(code = accountVerifyService.sendCode(request.email))
    }

    @Operation(
        summary = "이메일 인증 코드를 검증하는 API",
    )
    @PostMapping("/verify")
    fun verifyEmail(
        @RequestBody @Valid request: CodeRequest,
    ): AccountVerifyResponse {
        return AccountVerifyResponse(accountVerifyService.verifyCode(request.email, request.code))
    }

    @Operation(
        summary = "회원가입 API",
    )
    @PostMapping("/register")
    fun registerAccount(
        @RequestBody @Valid request: RegisterRequest,
    ): AuthToken {
        val account = accountService.createAccount(request.toCommand())
        return tokenProvider.generateToken(account)
    }

    @Operation(
        security = [
            SecurityRequirement(name = "Authorization"),
        ],
        summary = "내 정보를 불러오는 API",
    )
    @GetMapping("/me")
    fun getMyAccount(
        @RequestHeader("Authorization") access: String?,
    ): AccountResponse {
        if (access == null) throw PermissionDenyException()
        return try {
            AccountResponse.of(tokenProvider.getAuthentication(access))
        } catch (e: Exception) {
            throw PermissionDenyException()
        }
    }

    @Operation(
        summary = "로그인 API",
    )
    @PostMapping("/login")
    fun login(
        @RequestBody @Valid request: LoginRequest,
    ): AuthToken {
        return accountService.login(request.email, request.password)
    }
}
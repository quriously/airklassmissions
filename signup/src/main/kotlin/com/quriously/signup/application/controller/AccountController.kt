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
import io.swagger.v3.oas.annotations.Parameter
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
        description = "### 인증 코드 유의 사항\n\n" +
                "- 제일 마지막에 발급된 인증 코드만 유효 합니다.\n\n" +
                "- 인증 코드의 유효 시간은 1시간 입니다.\n\n" +
                "- 직접 Email을 발송하고 있지 않아 Response로 인증 코드를 전달합니다.\n\n"
    )

    fun sendVerifyEmail(
        @RequestBody @Valid request: EmailRequest,
    ): EmailCodeResponse {
        return EmailCodeResponse(code = accountVerifyService.sendCode(request.email))
    }

    @Operation(
        summary = "이메일 인증 코드를 검증하는 API",
        description = "### verifyId 란\n\n" +
                "- 특정 이메일에 대한 인증이 완료 되었는지 확인하기 위한 값 입니다.\n\n" +
                "- 회원가입시 verifyId 를 추가해야합니다."
    )
    @PostMapping("/verify")
    fun verifyEmail(
        @RequestBody @Valid request: CodeRequest,
    ): AccountVerifyResponse {
        return AccountVerifyResponse(accountVerifyService.verifyCode(request.email, request.code))
    }

    @Operation(
        summary = "회원가입 API",
        description = "### 약관 종류\n\n" +
                "- `SERVICE`: 서비스 이용약관 (필수)\n\n" +
                "- `PRIVACY`: 개인정보 이용약관 (필수)\n\n" +
                "- `MARKETING`: 마케팅 이용약관 (선택)\n\n" +
                "### 토큰\n\n" +
                "- `access`: 액세스 토큰 (만료시간 발급 후 30분)\n\n" +
                "- `refresh`: 리프레쉬 토큰 (만료시간 발급 후 2시간)\n\n"
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
        description = "### 유저 정보 조회\n\n" +
                "- 발급 받은 AccessToken을 이용하여 내 정보를 조회합니다.\n\n" +
                "- AccessToken은 Header의 `Authorization`에 담아서 보내야 합니다.\n\n" +
                "- 따로 TokenType은 입력 하지 않아도 됩니다. ex)Bearer, Basic, etc..."
    )
    @GetMapping("/me")
    fun getMyAccount(
        @Parameter(hidden=true) @RequestHeader("Authorization") access: String?,
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
        description = "### 로그인\n\n" +
                "- 이메일과 비밀번호를 통해 로그인 합니다.\n\n"
    )
    @PostMapping("/login")
    fun login(
        @RequestBody @Valid request: LoginRequest,
    ): AuthToken {
        return tokenProvider.generateToken(accountService.login(request.email, request.password))
    }
}
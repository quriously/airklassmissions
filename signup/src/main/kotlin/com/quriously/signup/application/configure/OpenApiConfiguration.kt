package com.quriously.signup.application.configure

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class OpenApiConfiguration {
    @Bean
    fun openAPI(): OpenAPI? {
        val authorizationSchemeName = "Authorization"

        return OpenAPI()
            .info(
                Info()
                    .title("회원 가입 API 서버")
                    .description("# API의 흐름은 다음과 같습니다.\n\n " +
                            "#### 1. 이메일 인증 코드 발송\n\n" +
                            "#### 2. 발송된 이메일 인증 코드 확인\n\n" +
                            "#### 3. 회원 가입\n\n" +
                            "#### 4. 회원 가입 또는 로그인 하여 발급 받은 Access Token으로 정보 조회\n\n" +
                            "# 유의사항\n\n" +
                            "#### DB는 H2를 사용하고 있어 데이터가 수시로 초기화 될 수 있습니다.\n\n"
                    )
            )
            .components(
                Components().addSecuritySchemes(
                    authorizationSchemeName,
                    SecurityScheme()
                        .type(SecurityScheme.Type.APIKEY)
                        .name("Authorization")
                        .`in`(SecurityScheme.In.HEADER)
                        .description("JWT Token은 Access Token을 추가해주셔야 합니다.")
                )
            )
    }
}
package com.quriously.signup.domain.service

import com.quriously.signup.domain.exception.InvalidDataException
import com.quriously.signup.domain.repository.AccountVerifyRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AccountVerifyServiceTest {
    private lateinit var accountVerifyRepository: AccountVerifyRepository
    private lateinit var accountVerifyService: AccountVerifyService

    @BeforeEach
    fun setUp() {
        accountVerifyRepository = FakeAccountVerifyRepository()
        accountVerifyService = AccountVerifyService(accountVerifyRepository)
    }

    @Test
    fun `이메일 발송 로직 테스트`(){
        //given
        val email = "taekyun@quriously.com"

        //when
        val code = accountVerifyService.sendCode(email)

        //then
        assertThat(code).isEqualTo(code)
    }
    
    @Test
    fun `인증 코드가 올바르지 않을때 테스트`(){
        //given
        val email = "taekyun@quriously.com"
        accountVerifyService.sendCode(email)

        //when
        val invalidCode = 124324

        //then
        assertThatThrownBy { accountVerifyService.verifyCode(email, invalidCode) }
            .isInstanceOf(InvalidDataException::class.java)
    }
}
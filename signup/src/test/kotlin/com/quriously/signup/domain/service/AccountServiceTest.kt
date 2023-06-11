package com.quriously.signup.domain.service

import com.quriously.signup.domain.dto.request.AccountRegisterCommand
import com.quriously.signup.domain.entity.AccountTermType
import com.quriously.signup.domain.exception.InvalidAccountAndAccountVerifyException
import com.quriously.signup.domain.exception.InvalidPasswordException
import com.quriously.signup.domain.exception.RequireTermException
import com.quriously.signup.domain.repository.AccountRepository
import com.quriously.signup.domain.repository.AccountVerifyRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class AccountServiceTest {
    private lateinit var accountRepository: AccountRepository
    private lateinit var accountVerifyRepository: AccountVerifyRepository
    private lateinit var accountVerifyService: AccountVerifyService
    private lateinit var accountService: AccountService

    @BeforeEach
    fun setUp() {
        accountRepository = FakeAccountRepository()
        accountVerifyRepository = FakeAccountVerifyRepository()
        accountVerifyService = AccountVerifyService(accountVerifyRepository)
        accountService = AccountService(accountRepository, accountVerifyRepository)
    }

    @Test
    fun `회원 가입 테스트`() {
        //given
        val email = "taekyun@quriously.com"
        val password = "1234"
        val passwordConfirm = password
        val terms = listOf(AccountTermType.SERVICE, AccountTermType.PRIVACY)
        val birthday = Date(1993, 7, 10)

        val code = accountVerifyService.sendCode(email)
        val accountVerify = accountVerifyRepository.getByEmailAndCode(email, code)

        //when
        val command = AccountRegisterCommand(
            email = email,
            accountVerifyId = accountVerify.id,
            password = password,
            passwordConfirm = passwordConfirm,
            terms = terms,
            birthday = birthday,
        )
        val entity = accountService.createAccount(command)

        //then
        assertThat(command.email).isEqualTo(entity.email)
        assertThat(command.password).isEqualTo(entity.password)
        assertThat(command.terms).contains(AccountTermType.SERVICE, AccountTermType.PRIVACY)
        assertThat(command.birthday).isEqualTo(entity.birthday)
    }

    @Test
    fun `회원 가입시 비밀번호와 검증 비밀번호가 다를때`() {
        //given
        val email = "taekyun@quriously.com"
        val password = "1234"
        val passwordConfirm = "12341234"
        val terms = listOf(AccountTermType.SERVICE, AccountTermType.PRIVACY)
        val birthday = Date(1993, 7, 10)

        val code = accountVerifyService.sendCode(email)
        val accountVerify = accountVerifyRepository.getByEmailAndCode(email, code)

        //when
        val command = AccountRegisterCommand(
            email = email,
            accountVerifyId = accountVerify.id,
            password = password,
            passwordConfirm = passwordConfirm,
            terms = terms,
            birthday = birthday,
        )


        //then
        assertThatThrownBy {
            accountService.createAccount(command)
        }.isInstanceOf(InvalidPasswordException::class.java)
    }

    @Test
    fun `회원 가입시 필수 약관 입력 되지 않았을 때`() {
        //given
        val email = "taekyun@quriously.com"
        val password = "1234"
        val passwordConfirm = password
        val terms = listOf(AccountTermType.SERVICE)
        val birthday = Date(1993, 7, 10)

        val code = accountVerifyService.sendCode(email)
        val accountVerify = accountVerifyRepository.getByEmailAndCode(email, code)

        //when
        val command = AccountRegisterCommand(
            email = email,
            accountVerifyId = accountVerify.id,
            password = password,
            passwordConfirm = passwordConfirm,
            terms = terms,
            birthday = birthday,
        )

        //then
        assertThatThrownBy {
            accountService.createAccount(command)
        }.isInstanceOf(RequireTermException::class.java)
    }

    @Test
    fun `회원 가입시 가입하려는 email과 인증 받은 emaild이 다를 경우`() {
        //given
        val email = "taekyun@quriously.com"
        val password = "1234"
        val passwordConfirm = password
        val terms = listOf(AccountTermType.SERVICE)
        val birthday = Date(1993, 7, 10)

        val verifyEmail = "taxijjang@quriously.com"
        val code = accountVerifyService.sendCode(verifyEmail)
        val accountVerify = accountVerifyRepository.getByEmailAndCode(verifyEmail, code)

        //when
        val command = AccountRegisterCommand(
            email = email,
            accountVerifyId = accountVerify.id,
            password = password,
            passwordConfirm = passwordConfirm,
            terms = terms,
            birthday = birthday,
        )

        //then
        assertThatThrownBy {
            accountService.createAccount(command)
        }.isInstanceOf(InvalidAccountAndAccountVerifyException::class.java)
    }

}
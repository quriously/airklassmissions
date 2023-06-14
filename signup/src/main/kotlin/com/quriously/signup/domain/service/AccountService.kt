package com.quriously.signup.domain.service

import com.quriously.signup.application.security.login.AuthToken
import com.quriously.signup.application.security.login.TokenProvider
import com.quriously.signup.domain.dto.request.AccountRegisterCommand
import com.quriously.signup.domain.entity.Account
import com.quriously.signup.domain.entity.AccountTermType
import com.quriously.signup.domain.entity.AccountVerify
import com.quriously.signup.domain.exception.*
import com.quriously.signup.domain.port.`in`.AccountRegisterMutatorUseCase
import com.quriously.signup.domain.repository.AccountRepository
import com.quriously.signup.domain.repository.AccountVerifyRepository
import org.springframework.transaction.annotation.Transactional

open class AccountService(
    private val accountRepository: AccountRepository,
    private val accountVerifyRepository: AccountVerifyRepository,
) : AccountRegisterMutatorUseCase {
    @Transactional
    override fun createAccount(command: AccountRegisterCommand): Account {
        val accountVerify: AccountVerify
        try {
            accountVerify = accountVerifyRepository.getById(command.accountVerifyId)
        } catch (e: Exception) {
            throw Exception("Invalid accountVerifyId")
        }

        if (accountVerify.email != command.email) {
            throw InvalidAccountAndAccountVerifyException()
        }


        if (command.password != command.passwordConfirm) {
            throw InvalidPasswordException()
        }

        this.checkPassword(command.password, command.passwordConfirm)

        if (!command.terms.contains(AccountTermType.SERVICE) || !command.terms.contains(AccountTermType.PRIVACY)) {
            throw RequireTermException()
        }

        val entity = Account(
            email = command.email,
            name = command.name,
            password = command.password,
            terms = command.terms.toSet(),
            birthday = command.birthday,
        )
        accountRepository.save(entity)
        return entity
    }

    override fun login(email: String, password: String): Account {
        return accountRepository.login(email, password)
    }

    private fun checkPassword(password: String, passwordConfirm: String): Boolean {
        val regex =
            "^(?!((?:[A-Za-z]+)|(?:[\\[\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-_+<>@\\#\$%&\\\\\\=\\(\\‘\\“]+)|(?:[0-9]+))\$)[A-Za-z\\d\\[\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-_+<>@\\#\$%&\\\\\\=\\(\\’\\“]{10,20}\$".toRegex()
        if (!(regex.matches(password) && regex.matches(passwordConfirm))) throw InvalidPasswordFormatException()
        return password == passwordConfirm
    }
}
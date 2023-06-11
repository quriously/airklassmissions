package com.quriously.signup.domain.service

import com.quriously.signup.domain.dto.request.AccountRegisterCommand
import com.quriously.signup.domain.entity.Account
import com.quriously.signup.domain.entity.AccountTermType
import com.quriously.signup.domain.entity.AccountVerify
import com.quriously.signup.domain.exception.InvalidAccountAndAccountVerifyException
import com.quriously.signup.domain.exception.InvalidPasswordException
import com.quriously.signup.domain.exception.RequireTermException
import com.quriously.signup.domain.repository.AccountRepository
import com.quriously.signup.domain.repository.AccountVerifyRepository
import org.springframework.transaction.annotation.Transactional

open class AccountService(
    private val accountRepository: AccountRepository,
    private val accountVerifyRepository: AccountVerifyRepository,
) {
    @Transactional
    open fun createAccount(command: AccountRegisterCommand): Account {
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

        if (!command.terms.contains(AccountTermType.SERVICE) || !command.terms.contains(AccountTermType.PRIVACY)) {
            throw RequireTermException()
        }

        val entity = Account(
            email = command.email,
            password = command.password,
            terms = command.terms.toSet(),
            birthday = command.birthday,
        )
        accountRepository.save(entity)
        return entity
    }


}
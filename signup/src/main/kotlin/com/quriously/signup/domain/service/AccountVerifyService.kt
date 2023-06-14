package com.quriously.signup.domain.service

import com.quriously.signup.domain.entity.AccountVerify
import com.quriously.signup.domain.exception.AlreadyRegisterAccountException
import com.quriously.signup.domain.exception.InvalidDataException
import com.quriously.signup.domain.port.`in`.AccountVerifyMutatorUseCase
import com.quriously.signup.domain.repository.AccountRepository
import com.quriously.signup.domain.repository.AccountVerifyRepository
import org.springframework.transaction.annotation.Transactional

open class AccountVerifyService(
    private val accountRepository: AccountRepository,
    private val accountVerifyRepository: AccountVerifyRepository,
) : AccountVerifyMutatorUseCase {
    override fun sendCode(email: String): String {
        if (accountRepository.exists(email)) {
            throw AlreadyRegisterAccountException(email)
        }

        val accountVerify = AccountVerify(
            email = email,
        )
        val entity = accountVerifyRepository.save(accountVerify)
        return entity.code.toString()
    }

    @Transactional
    override fun verifyCode(email: String, code: String): Long {
        val entity = accountVerifyRepository.getTopByEmail(email)
        if (entity.code != code) throw InvalidDataException()
        return entity.verify(code)
    }
}
package com.quriously.signup.domain.service

import com.quriously.signup.domain.entity.AccountVerify
import com.quriously.signup.domain.repository.AccountVerifyRepository
import org.springframework.transaction.annotation.Transactional

open class AccountVerifyService(
    private val accountVerifyRepository: AccountVerifyRepository,
) {
    fun sendCode(email: String): Int {
        val accountVerify = AccountVerify(
            email = email,
        )
        val entity = accountVerifyRepository.save(accountVerify)
        return entity.code
    }

    @Transactional
    open fun verifyCode(email: String, code: Int): Boolean {
        val entity = accountVerifyRepository.getByEmailAndCode(email, code)
        return entity.verify(code)
    }
}
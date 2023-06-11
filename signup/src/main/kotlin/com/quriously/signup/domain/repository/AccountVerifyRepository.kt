package com.quriously.signup.domain.repository

import com.quriously.signup.domain.entity.AccountVerify

interface AccountVerifyRepository {
    fun save(entity: AccountVerify): AccountVerify
    fun getById(id: Long): AccountVerify
    fun getByEmailAndCode(email: String, code: Int): AccountVerify
}
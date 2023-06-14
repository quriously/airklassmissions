package com.quriously.signup.domain.repository

import com.quriously.signup.domain.entity.Account

interface AccountRepository {
    fun save(entity: Account): Account
    fun getByEmail(email: String): Account
    fun exists(email: String): Boolean
}
package com.quriously.signup.domain.service

import com.quriously.signup.domain.entity.Account
import com.quriously.signup.domain.repository.AccountRepository

class FakeAccountRepository: AccountRepository {
    private val map: MutableMap<Long, Account> = mutableMapOf()
    override fun save(entity: Account): Account {
        map[entity.id] = entity
        return map[entity.id]!!
    }

    override fun getByEmail(email: String): Account {
        return map.values.first { it.email == email }
    }

    override fun login(email: String, password: String): Account {
        return map.values.first { it.email == email && it.password == password }
    }

    override fun exists(email: String): Boolean {
        return map.values.any { it.email == email }
    }
}
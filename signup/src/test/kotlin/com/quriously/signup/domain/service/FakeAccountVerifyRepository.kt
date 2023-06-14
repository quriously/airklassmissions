package com.quriously.signup.domain.service

import com.quriously.signup.domain.entity.AccountVerify
import com.quriously.signup.domain.exception.InvalidDataException
import com.quriously.signup.domain.repository.AccountVerifyRepository

class FakeAccountVerifyRepository: AccountVerifyRepository {
    private val map = mutableMapOf<String, AccountVerify>()

    override fun save(entity: AccountVerify): AccountVerify {
        map[entity.email] = entity
        return entity
    }

    override fun getById(id: Long): AccountVerify {
        return map.values.first { it.id == id }
    }

    override fun getByEmailAndCode(email: String, code: String): AccountVerify {
        map[email]?.let {
            if(it.code == code){
                return map[email]!!
            }
        }
        throw InvalidDataException()
    }

    override fun getTopByEmail(email: String): AccountVerify {
        TODO("Not yet implemented")
    }

}
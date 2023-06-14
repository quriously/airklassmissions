package com.quriously.signup.application.repository

import com.quriously.signup.domain.entity.Account
import com.quriously.signup.domain.exception.LoginException
import com.quriously.signup.domain.exception.NotFoundEmailException
import com.quriously.signup.domain.repository.AccountRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class AccountRepositoryImpl(
    private val jpaAccountRepository: JpaAccountRepository,
) : AccountRepository {
    override fun save(entity: Account): Account {
        return jpaAccountRepository.save(entity)
    }

    override fun getByEmail(email: String): Account {
        return try {
            jpaAccountRepository.getByEmail(email)
        } catch (e: Exception) {
            throw NotFoundEmailException()
        }
    }

    override fun login(email: String, password: String): Account {
        return try{
            jpaAccountRepository.getByEmailAndPassword(email, password)
        } catch (e: Exception) {
            throw LoginException()
        }
    }

    override fun exists(email: String): Boolean {
        return try {
            jpaAccountRepository.getByEmail(email)
            true
        } catch (e: Exception) {
            false
        }
    }
}

interface JpaAccountRepository : JpaRepository<Account, Long> {
    fun getByEmail(email: String): Account
    fun findByEmail(email: String): Account?
    fun getByEmailAndPassword(email: String, password: String): Account
}
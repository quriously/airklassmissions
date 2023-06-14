package com.quriously.signup.application.repository

import com.quriously.signup.domain.entity.AccountVerify
import com.quriously.signup.domain.exception.InvalidDataException
import com.quriously.signup.domain.exception.InvalidVerifyDataException
import com.quriously.signup.domain.repository.AccountVerifyRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Service
class AccountVerifyRepositoryImpl(
    private val jpaAccountVerifyRepository: JpaAccountVerifyRepository,
) : AccountVerifyRepository {
    override fun save(entity: AccountVerify): AccountVerify {
        return jpaAccountVerifyRepository.save(entity)
    }

    override fun getById(id: Long): AccountVerify {
        return try {
            jpaAccountVerifyRepository.getById(id)
        } catch (e: EntityNotFoundException) {
            throw InvalidVerifyDataException()
        }
    }

    override fun getByEmailAndCode(email: String, code: String): AccountVerify {
        return try{
            jpaAccountVerifyRepository.findAllByEmailAndCode(email, code).first()
        } catch (e: NoSuchElementException) {
            throw InvalidDataException()
        }
    }

    override fun getTopByEmail(email: String): AccountVerify {
        return try{
            jpaAccountVerifyRepository.findAllByEmail(email).last()
        } catch (e: NoSuchElementException) {
            throw InvalidDataException()
        }
    }
}

@Repository
interface JpaAccountVerifyRepository : JpaRepository<AccountVerify, Long> {
    fun save(entity: AccountVerify): AccountVerify
    fun findByEmailAndCode(email: String, code: String): AccountVerify?
    fun getByEmail(email: String): AccountVerify
    fun findAllByEmail(email: String): List<AccountVerify>
    fun findAllByEmailAndCode(email: String, code: String): List<AccountVerify>
}
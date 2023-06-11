package com.quriously.signup.domain.entity

import com.quriously.signup.domain.exception.ExpiredCodeException
import com.quriously.signup.domain.exception.InvalidDataException
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.ZonedDateTime

@Entity
class AccountVerify(
    val email: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    val code: Int = (111111..999999).random()
    val expiredAt: ZonedDateTime = ZonedDateTime.now().plusHours(1)
    val sendedAt: ZonedDateTime = ZonedDateTime.now()
    var verifiedAt: ZonedDateTime? = null
        private set

    fun verify(code: Int): Boolean {
        if (this.code == code) {
            verifiedAt = ZonedDateTime.now()
            return true
        }else if(this.expiredAt.isAfter(ZonedDateTime.now())){
            throw ExpiredCodeException()
        }
        throw InvalidDataException()
    }
}
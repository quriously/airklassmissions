package com.quriously.signup.domain.entity

import jakarta.persistence.CollectionTable
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import java.time.ZonedDateTime
import java.util.Date

@Entity
class Account(
    val email: String,
    val password: String,

    @ElementCollection
    @CollectionTable(name = "account_terms", joinColumns = [JoinColumn(name = "account_id")])
    val terms: Set<AccountTermType>,

    val birthday: Date,

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    val createdAt: ZonedDateTime = ZonedDateTime.now()
    val updatedAt: ZonedDateTime = ZonedDateTime.now()

    fun checkTerms(terms: Set<AccountTermType>): Boolean {
        return terms.contains(AccountTermType.SERVICE) && terms.contains(AccountTermType.PRIVACY)
    }

}
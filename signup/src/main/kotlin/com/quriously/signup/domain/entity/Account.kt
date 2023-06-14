package com.quriously.signup.domain.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.time.ZonedDateTime

@Entity
class Account(
    @Column(unique = true)
    val email: String,
    val name: String,
    val password: String,

    @ElementCollection
    @CollectionTable(name = "account_terms", joinColumns = [JoinColumn(name = "account_id")])
    val terms: Set<AccountTermType>,

    val birthday: LocalDate,

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
package com.quriously.signup.application.dto.response

import com.quriously.signup.domain.entity.Account

data class AccountResponse(
    val id: Long,
    val name: String,
    val email: String,
    val birthday: String,
    val terms: List<String>,
) {
    companion object{
        fun of(account: Account): AccountResponse {
            return AccountResponse(
                id = account.id,
                name = account.name,
                email = account.email,
                birthday = account.birthday.toString(),
                terms = account.terms.map { it.name }
            )
        }
    }
}
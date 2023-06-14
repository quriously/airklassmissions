package com.quriously.signup.application.security.login

import com.quriously.signup.domain.entity.Account
import com.quriously.signup.domain.exception.PermissionDenyException
import com.quriously.signup.domain.repository.AccountRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class TokenProvider(
    private val accountRepository: AccountRepository,
    @Value("\${env.secret-key}")
    private val secretKey: String,
) {
    fun generateToken(account: Account): AuthToken {
        val claims = Jwts.claims().setSubject("access")
        claims.issuer = "quriously"
        claims["id"] = account.id
        claims["email"] = account.email

        val now = Date()
        val accessExpireDate = Date(now.time + 1000 * 60 * 30)
        val refreshExpireDate = Date(now.time + 1000 * 60 * 60 * 2)
        return AuthToken(
            access = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date())
                .setExpiration(accessExpireDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact(),
            refresh = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date())
                .setExpiration(refreshExpireDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact(),
        )
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            true
        } catch (e: Exception) {
            throw PermissionDenyException()
        }
    }

    fun getAuthentication(token: String): Account {
        this.validateToken(token)
        return accountRepository.getByEmail(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body["email"].toString())
    }
}
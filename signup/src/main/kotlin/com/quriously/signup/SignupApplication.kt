package com.quriously.signup

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@SpringBootApplication
@EntityScan(
    "com.quriously.signup.domain",
)
class SignupApplication

fun main(args: Array<String>) {
    runApplication<SignupApplication>(*args)
}

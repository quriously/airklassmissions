package com.quriously.signup.application.configure.bean

import com.quriously.signup.application.security.login.TokenProvider
import com.quriously.signup.domain.repository.AccountRepository
import com.quriously.signup.domain.repository.AccountVerifyRepository
import com.quriously.signup.domain.service.AccountService
import com.quriously.signup.domain.service.AccountVerifyService
import org.springframework.stereotype.Service


@Service
class AccountServiceImpl(
    accountRepository: AccountRepository,
    accountVerifyRepository: AccountVerifyRepository,
    tokenProvider: TokenProvider,
) : AccountService(
    accountRepository,
    accountVerifyRepository,
    tokenProvider,
)

@Service
class AccountVerifyServiceImpl(
    accountRepository: AccountRepository,
    accountVerifyRepository: AccountVerifyRepository,
) : AccountVerifyService(
    accountRepository,
    accountVerifyRepository
)
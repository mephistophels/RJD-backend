package com.mephistophels.rjd.service.impl.auth

import com.mephistophels.rjd.database.entity.user.User
import com.mephistophels.rjd.security.model.Authority
import com.mephistophels.rjd.security.JwtParser
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class JwtHelper(
    private val jwt: JwtParser,

    @Value("\${spring.security.jwt.access.lifetime}")
    private val accessTokenLifetime: Long,
) {

    fun generateAccessToken(user: User): String {
        val authorities = mutableListOf(Authority.USER)
        return jwt.createToken(
            "userId" to user.id,
            "permissions" to emptyList<String>(),
            "authorities" to authorities,
            expiration = getAccessTokenExpiration(),
        )
    }

    private fun getAccessTokenExpiration(): Date =
        Instant.now().plus(accessTokenLifetime, ChronoUnit.DAYS).let { Date.from(it) }

}
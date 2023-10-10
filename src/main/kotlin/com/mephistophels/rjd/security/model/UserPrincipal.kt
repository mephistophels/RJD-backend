package com.mephistophels.rjd.security.model

import com.mephistophels.rjd.util.USER_ROLE
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority


class UserPrincipal(
    val userId: Long,
    val authorities: List<GrantedAuthority> = listOf(GrantedAuthority { USER_ROLE })
) : AbstractAuthenticationToken(authorities) {
    override fun getCredentials() = null
    override fun getPrincipal() = userId
}

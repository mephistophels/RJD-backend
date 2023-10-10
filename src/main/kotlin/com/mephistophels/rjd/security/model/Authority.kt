package com.mephistophels.rjd.security.model

import org.springframework.security.core.GrantedAuthority
import com.mephistophels.rjd.util.*

enum class Authority(val authority: GrantedAuthority) {
    USER(GrantedAuthority { USER_ROLE })
}
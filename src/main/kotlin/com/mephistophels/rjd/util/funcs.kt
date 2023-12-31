package com.mephistophels.rjd.util

import org.springframework.security.core.context.SecurityContextHolder

// returns user id
fun getPrincipal(): Long = (SecurityContextHolder.getContext().authentication.principal as Long)

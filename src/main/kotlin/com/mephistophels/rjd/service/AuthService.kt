package com.mephistophels.rjd.service

import com.mephistophels.rjd.model.request.LoginRequest
import com.mephistophels.rjd.model.request.RegistrationRequest
import com.mephistophels.rjd.model.response.LoginResponse
import com.mephistophels.rjd.model.response.user.UserResponse

interface AuthService {
    fun login(request: LoginRequest): LoginResponse
    fun register(request: RegistrationRequest): UserResponse
}
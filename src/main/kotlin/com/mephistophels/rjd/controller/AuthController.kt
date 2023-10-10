package com.mephistophels.rjd.controller

import com.mephistophels.rjd.model.request.LoginRequest
import com.mephistophels.rjd.model.request.RegistrationRequest
import com.mephistophels.rjd.model.response.LoginResponse
import com.mephistophels.rjd.model.response.user.UserResponse
import com.mephistophels.rjd.service.AuthService
import com.mephistophels.rjd.util.API_PUBLIC
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("$API_PUBLIC/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): LoginResponse {
        return authService.login(request)
    }

    @PostMapping("/registration")
    fun register(@Valid @RequestBody request: RegistrationRequest): UserResponse {
        return authService.register(request)
    }


}
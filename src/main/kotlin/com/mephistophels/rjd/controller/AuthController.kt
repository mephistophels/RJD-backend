package com.mephistophels.rjd.controller

import com.mephistophels.rjd.model.request.LoginRequest
import com.mephistophels.rjd.model.request.RegistrationRequest
import com.mephistophels.rjd.model.response.LoginResponse
import com.mephistophels.rjd.model.response.user.UserResponse
import com.mephistophels.rjd.service.AuthService
import com.mephistophels.rjd.service.UserService
import com.mephistophels.rjd.util.API_PUBLIC
import jakarta.servlet.http.Part
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("$API_PUBLIC/auth")
class AuthController(
    private val authService: AuthService,
    private val userService: UserService,
) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): LoginResponse {
        return authService.login(request)
    }

    @PostMapping("/registration")
    fun register(@Valid @RequestBody request: RegistrationRequest, @RequestPart("photo") photo : Part): UserResponse {
        val user = authService.register(request)
        return userService.uploadUserPhoto(user.id, photo)
    }
}
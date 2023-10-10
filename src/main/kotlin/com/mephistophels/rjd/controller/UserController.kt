package com.mephistophels.rjd.controller

import com.mephistophels.rjd.model.response.user.UserFullResponse
import com.mephistophels.rjd.model.response.user.UserMediumResponse
import com.mephistophels.rjd.service.UserService
import com.mephistophels.rjd.util.API_VERSION_1
import com.mephistophels.rjd.util.getPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("$API_VERSION_1/user")
class UserController(
    private val service: UserService
) {

    @GetMapping("/me")
    fun getSelfProfile(): UserFullResponse {
        return service.getSelfProfile(getPrincipal())
    }

    @GetMapping("/{userId}")
    fun getUserProfile(@PathVariable userId: Long): UserMediumResponse {
        return service.getUserProfile(userId)
    }
}
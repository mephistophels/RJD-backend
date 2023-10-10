package com.mephistophels.rjd.service

import com.mephistophels.rjd.database.entity.User
import com.mephistophels.rjd.model.request.RegistrationRequest
import com.mephistophels.rjd.model.response.user.UserFullResponse
import com.mephistophels.rjd.model.response.user.UserMediumResponse

interface UserService {
    fun createUser(request: RegistrationRequest): User
    fun existByEmail(email: String): Boolean
    fun findEntityByEmail(email: String): User
    fun findEntityById(id: Long): User
    fun getSelfProfile(userId: Long): UserFullResponse
    fun getUserProfile(userId: Long): UserMediumResponse
}
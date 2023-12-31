package com.mephistophels.rjd.service

import com.mephistophels.rjd.database.entity.user.User
import com.mephistophels.rjd.model.request.RegistrationRequest
import com.mephistophels.rjd.model.request.common.PageRequest
import com.mephistophels.rjd.model.request.user.CompanionRequest
import com.mephistophels.rjd.model.response.common.PageResponse
import com.mephistophels.rjd.model.response.user.CompanionResponse
import com.mephistophels.rjd.model.response.user.UserFullResponse
import com.mephistophels.rjd.model.response.user.UserMediumResponse
import com.mephistophels.rjd.model.response.user.UserResponse
import jakarta.servlet.http.Part

interface UserService {
    fun createUser(request: RegistrationRequest): User
    fun existByEmail(email: String): Boolean
    fun findEntityByEmail(email: String): User
    fun findEntityById(id: Long): User
    fun getSelfProfile(userId: Long): UserFullResponse
    fun getUserProfile(userId: Long): UserMediumResponse
    fun getCompanionsList(request: PageRequest): PageResponse<CompanionResponse>
    fun createCompanion(request: CompanionRequest): CompanionResponse
    fun uploadCompanionPhoto(companionId: Long, photo: Part): CompanionResponse
    fun deleteCompanion(id: Long): CompanionResponse
    fun findUserOrCompanionById(id: Long): Any
    fun getCompanion(id: Long): CompanionResponse

    fun uploadUserPhoto(userId: Long, photo: Part) : UserResponse
}
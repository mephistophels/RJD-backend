package com.mephistophels.rjd.service.impl.user

import com.mephistophels.rjd.database.entity.User
import com.mephistophels.rjd.database.repository.UserDao
import com.mephistophels.rjd.errors.AlreadyExistsException
import com.mephistophels.rjd.errors.ResourceNotFoundException
import com.mephistophels.rjd.mappers.UserMapper
import com.mephistophels.rjd.model.request.RegistrationRequest
import com.mephistophels.rjd.model.response.user.UserFullResponse
import com.mephistophels.rjd.model.response.user.UserMediumResponse
import com.mephistophels.rjd.service.UserService
import org.springframework.context.annotation.Lazy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val mapper: UserMapper,
    private val dao: UserDao,
) : UserService {

    override fun existByEmail(email: String): Boolean {
        return dao.existsByEmail(email)
    }

    override fun findEntityByEmail(email: String) : User {
        return dao.findByEmail(email).orElseThrow { ResourceNotFoundException(email, User::class.java) }
    }

    override fun findEntityById(id: Long): User {
        return dao.findById(id).orElseThrow { ResourceNotFoundException(id, User::class.java) }
    }

    override fun getSelfProfile(userId: Long): UserFullResponse {
        val user = findEntityById(userId)
        return mapper.asUserFullResponse(user,)
    }

    override fun getUserProfile(userId: Long): UserMediumResponse {
        val user = findEntityById(userId)
        return mapper.asUserMediumResponse(user)
    }

    override fun createUser(request: RegistrationRequest): User {
        if (dao.existsByEmail(request.email)) {
            throw AlreadyExistsException(request.email)
        }
        val user = mapper.asEntity(request).apply {
            hash = passwordEncoder.encode(request.password)
            dao.save(this)
        }
        return user
    }

}
package com.mephistophels.rjd.service.impl.user

import com.mephistophels.rjd.database.entity.user.Companion
import com.mephistophels.rjd.database.entity.Tag
import com.mephistophels.rjd.database.entity.user.User
import com.mephistophels.rjd.database.repository.CompanionDao
import com.mephistophels.rjd.database.repository.TagDao
import com.mephistophels.rjd.database.repository.UserDao
import com.mephistophels.rjd.errors.AlreadyExistsException
import com.mephistophels.rjd.errors.ApiError
import com.mephistophels.rjd.errors.ResourceNotFoundException
import com.mephistophels.rjd.mappers.UserMapper
import com.mephistophels.rjd.model.enums.PhotoPath
import com.mephistophels.rjd.model.request.RegistrationRequest
import com.mephistophels.rjd.model.request.common.PageRequest
import com.mephistophels.rjd.model.request.user.CompanionRequest
import com.mephistophels.rjd.model.response.common.PageResponse
import com.mephistophels.rjd.model.response.user.CompanionResponse
import com.mephistophels.rjd.model.response.user.UserFullResponse
import com.mephistophels.rjd.model.response.user.UserMediumResponse
import com.mephistophels.rjd.service.StorageManager
import com.mephistophels.rjd.service.UserService
import com.mephistophels.rjd.util.getPrincipal
import jakarta.servlet.http.Part
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
@Transactional
class UserServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val mapper: UserMapper,
    private val dao: UserDao,
    private val companionDao: CompanionDao,
    private val tagDao: TagDao,
    private val storageManager: StorageManager
) : UserService {

    override fun existByEmail(email: String): Boolean {
        return dao.existsByEmail(email)
    }

    override fun findUserOrCompanionById(id: Long): Any {
        return dao.findById(id).getOrNull() ?: companionDao.findById(id).getOrNull()
            ?: throw ResourceNotFoundException(id, User::class.java)
    }

    private fun findCompanionEntityById(id: Long): Companion {
        return companionDao.findById(id).orElseThrow { ResourceNotFoundException(id, Companion::class.java) }
    }

    override fun getCompanion(id: Long): CompanionResponse {
        val entity = findCompanionEntityById(id)
        return mapper.asResponse(entity)
    }

    override fun findEntityByEmail(email: String): User {
        return dao.findByEmail(email).orElseThrow { ResourceNotFoundException(email, User::class.java) }
    }

    override fun findEntityById(id: Long): User {
        return dao.findById(id).orElseThrow { ResourceNotFoundException(id, User::class.java) }
    }

    override fun getSelfProfile(userId: Long): UserFullResponse {
        val user = findEntityById(userId)
        return mapper.asUserFullResponse(user)
    }

    override fun getUserProfile(userId: Long): UserMediumResponse {
        val user = findEntityById(userId)
        return mapper.asUserMediumResponse(user)
    }

    override fun getCompanionsList(request: PageRequest): PageResponse<CompanionResponse> {
        val user = findEntityById(getPrincipal())
        val companions = companionDao.findAllByHolder(user, request.pageable)
        return mapper.asPageResponse(companions)
    }

    override fun createCompanion(request: CompanionRequest): CompanionResponse {
        val user = findEntityById(getPrincipal())
        val companion = mapper.asEntity(request).apply {
            this.holder = user
        }.also { companionDao.save(it) }
        return mapper.asResponse(companion)
    }

    private fun findCompanionAndCheckHolder(companionId: Long, holder: User): Companion {
        val companion = findCompanionEntityById(companionId)
        if (companion.holder.id != holder.id) throw ApiError(
            status = HttpStatus.BAD_REQUEST,
            "Нельзя менять чужого попутчика"
        )
        return companion
    }

    override fun uploadCompanionPhoto(companionId: Long, photo: Part): CompanionResponse {
        val holder = findEntityById(getPrincipal())
        val companion = findCompanionAndCheckHolder(companionId, holder).apply {
            this.avatar = storageManager.saveImage(photo, PhotoPath.USER, this.avatar)
        }
        return mapper.asResponse(companion)
    }

    override fun deleteCompanion(id: Long): CompanionResponse {
        val user = findEntityById(getPrincipal())
        val companion = findCompanionAndCheckHolder(id, user).also { companionDao.delete(it) }
        storageManager.removeImage(companion.avatar, PhotoPath.USER)
        return mapper.asResponse(companion)
    }

    override fun createUser(request: RegistrationRequest): User {
        if (dao.existsByEmail(request.email)) {
            throw AlreadyExistsException(request.email)
        }
        val user = mapper.asEntity(request).apply {
            hash = passwordEncoder.encode(request.password)
            dao.save(this)
        }
        for (tag in request.tag) {
            tagDao.save(Tag(tag.lowercase(Locale.getDefault())).apply { this.user = user })
        }
        user.apply {
            this.tag = tagDao.findAllByUser(this)
        }
        return user
    }

}
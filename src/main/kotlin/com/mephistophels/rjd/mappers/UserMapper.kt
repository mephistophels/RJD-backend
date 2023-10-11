package com.mephistophels.rjd.mappers

import com.mephistophels.rjd.database.entity.Companion
import com.mephistophels.rjd.database.entity.User
import com.mephistophels.rjd.model.request.RegistrationRequest
import com.mephistophels.rjd.model.request.user.CompanionRequest
import com.mephistophels.rjd.model.response.common.PageResponse
import com.mephistophels.rjd.model.response.user.CompanionResponse
import com.mephistophels.rjd.model.response.user.UserFullResponse
import com.mephistophels.rjd.model.response.user.UserMediumResponse
import com.mephistophels.rjd.model.response.user.UserResponse
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component


@Component
class UserMapper {
    fun asEntity(request: RegistrationRequest): User {
        return User(
            email = request.email,
            name = request.name,
            patronymic = request.patronymic,
            surname = request.surname,
            birthday = request.birthday,
            bio = request.bio,
            phone = request.phone,
            avatar = request.avatar,
        )
    }
    fun asNullableResponse(entity: User?): UserResponse? {
        return if (entity == null) null else asResponse(entity)
    }

    fun asResponse(entity: User): UserResponse {
        val res = mutableSetOf<String>()
        for (tag in entity.tag) { res.add(tag.tag) }
        return UserResponse(
            id = entity.id,
            createdAt = entity.createdAt,
            email = entity.email,
            name = entity.name,
            patronymic = entity.patronymic,
            surname = entity.surname,
            birthday = entity.birthday,
            tag = res,
        )
    }

    fun asUserFullResponse(entity: User) : UserFullResponse {
        val res = mutableSetOf<String>()
        for (tag in entity.tag) { res.add(tag.tag) }
        return UserFullResponse(
            id = entity.id,
            createdAt = entity.createdAt,
            email = entity.email,
            name = entity.name,
            patronymic = entity.patronymic,
            surname = entity.surname,
            birthday = entity.birthday,
            bio = entity.bio,
            tag = res,

        )
    }

    fun asUserMediumResponse(entity: User) : UserMediumResponse {
        val res = mutableSetOf<String>()
        for (tag in entity.tag) { res.add(tag.tag) }
        return UserMediumResponse(
            id = entity.id,
            createdAt = entity.createdAt,
            email = entity.email,
            name = entity.name,
            patronymic = entity.patronymic,
            surname = entity.surname,
            birthday = entity.birthday,
            bio = entity.bio,
            tag = res,
        )
    }

    fun asResponse(entity: Companion): CompanionResponse {
        return CompanionResponse(
            id = entity.id,
            createdAt = entity.createdAt,
            surname = entity.surname,
            name = entity.name,
            patronymic = entity.patronymic,
            birthday = entity.birthday,
            phone = entity.phone,
            bio = entity.bio,
            avatar = entity.avatar
        )
    }

    fun asPageResponse(page: Page<Companion>): PageResponse<CompanionResponse> {
        return PageResponse.build(page, ::asResponse)
    }

    fun asEntity(request: CompanionRequest): Companion {
        return Companion(
            name = request.name,
            patronymic = request.patronymic,
            surname = request.surname,
            birthday = request.birthday,
            bio = request.bio,
            phone = request.phone
        )
    }
}
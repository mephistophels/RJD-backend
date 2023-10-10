package com.mephistophels.rjd.model.response.user

import java.time.LocalDate
import java.time.LocalDateTime

open class UserMediumResponse(
    id: Long,
    createdAt: LocalDateTime,
    email: String,
    surname: String,
    name: String,
    patronymic: String? = null,
    birthday: LocalDate? = null,
    val bio: String? = null,
) : UserResponse(id, createdAt, email, surname, name, patronymic, birthday)

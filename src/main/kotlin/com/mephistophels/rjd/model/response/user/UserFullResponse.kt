package com.mephistophels.rjd.model.response.user

import java.time.LocalDate
import java.time.LocalDateTime

class UserFullResponse(
    id: Long,
    createdAt: LocalDateTime,
    email: String,
    surname: String,
    name: String,
    patronymic: String? = null,
    birthday: LocalDate? = null,
    bio: String? = null,
    tag: Set<String>
) : UserMediumResponse(id, createdAt, email, surname, name, patronymic, birthday, bio, tag)

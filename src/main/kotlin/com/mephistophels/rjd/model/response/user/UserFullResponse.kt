package com.mephistophels.rjd.model.response.user

import com.mephistophels.rjd.model.request.Questionnaire
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
    phone: String,
    questionnaire: Questionnaire,
    avatar: String?,
) : UserMediumResponse(id, createdAt, email, surname, name, patronymic, birthday, phone, questionnaire, avatar)

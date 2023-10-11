package com.mephistophels.rjd.model.response.user

import com.mephistophels.rjd.model.response.common.AbstractCreatedAtResponse
import java.time.LocalDate
import java.time.LocalDateTime

class UserShortResponse(
    id: Long,
    createdAt: LocalDateTime,
    val surname: String,
    val name: String,
    val patronymic: String? = null,
    val birthday: LocalDate,
    val tag: Set<String>,
    val mark: UserMarkResponse
) : AbstractCreatedAtResponse(id, createdAt)
package com.mephistophels.rjd.model.response.user

import com.mephistophels.rjd.model.response.common.AbstractCreatedAtResponse
import java.time.LocalDate
import java.time.LocalDateTime

class CompanionResponse(
    id: Long,
    createdAt: LocalDateTime,
    val surname: String,
    val name: String,
    val patronymic: String? = null,
    val birthday: LocalDate,
    val phone: String? = null,
    val bio: String? = null,
    val avatar: String? = null,
): AbstractCreatedAtResponse(id, createdAt)
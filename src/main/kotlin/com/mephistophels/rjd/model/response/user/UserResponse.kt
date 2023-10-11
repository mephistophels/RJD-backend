package com.mephistophels.rjd.model.response.user

import com.mephistophels.rjd.model.response.common.AbstractCreatedAtResponse
import java.time.LocalDate
import java.time.LocalDateTime

open class UserResponse(
    id: Long,
    createdAt: LocalDateTime,
    val email: String,
    val surname: String,
    val name: String,
    val patronymic: String? = null,
    val birthday: LocalDate? = null,
    val tag: Set<String>,
): AbstractCreatedAtResponse(id, createdAt)
package com.mephistophels.rjd.model.request.user

import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.time.LocalDateTime

class CompanionRequest(
    @field:Size(max = 255)
    val surname: String,
    @field:Size(max = 255)
    val name: String,
    @field:Size(max = 255)
    val patronymic: String? = null,
    val birthday: LocalDate,
    @field:Size(max = 11)
    val phone: String? = null,
    @field:Size(max = 3000)
    val bio: String? = null,
)
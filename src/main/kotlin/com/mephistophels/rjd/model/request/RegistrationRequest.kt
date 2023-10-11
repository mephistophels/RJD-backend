package com.mephistophels.rjd.model.request

import com.mephistophels.rjd.database.entity.Tag
import com.mephistophels.rjd.util.EMAIL_REGEX
import jakarta.validation.constraints.Pattern
import java.time.LocalDate
import jakarta.validation.constraints.Size
import org.apache.tika.sax.CleanPhoneText

class RegistrationRequest(
    @field:Pattern(regexp = EMAIL_REGEX)
    @field:Size(min = 4, max = 120)
    val email: String,
    @field:Size(min = 8, max = 255)
    val password: String,
    @field:Size(max = 255)
    val surname: String,
    @field:Size(max = 255)
    val name: String,
    @field:Size(max = 255)
    val patronymic: String? = null,
    val birthday: LocalDate? = null,
    @field:Size(max = 3000)
    var bio: String? = null,
    @field:Size(max = 11)
    var phone: String,
    var avatar: String,
    var tag: Set<String>,
)
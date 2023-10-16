package com.mephistophels.rjd.model.request

import com.mephistophels.rjd.database.entity.Tag
import com.mephistophels.rjd.database.entity.user.Answer
import jakarta.validation.constraints.Size

data class Questionnaire(
    @field:Size(max = 3000)
    var bio: String,
    var tags: Set<String>,
    var answers: List<Int>,
)
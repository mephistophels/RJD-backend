package com.mephistophels.rjd.model.request

import jakarta.validation.constraints.Max

class MarkRequest(
    val mark: Int,
    @field:Max(2000)
    val message: String
)
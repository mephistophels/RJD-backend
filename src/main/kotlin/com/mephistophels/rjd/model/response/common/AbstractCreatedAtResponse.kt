package com.mephistophels.rjd.model.response.common

import java.time.LocalDateTime

abstract class AbstractCreatedAtResponse(
    id: Long,
    val createdAt: LocalDateTime
): AbstractResponse(id)
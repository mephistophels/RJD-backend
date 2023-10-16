package com.mephistophels.rjd.model.request

import java.time.LocalDateTime

class RecommendationRequest(
    val date: LocalDateTime,
    val trainNumber: Long,
)
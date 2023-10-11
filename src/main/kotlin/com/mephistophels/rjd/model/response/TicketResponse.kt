package com.mephistophels.rjd.model.response

import com.mephistophels.rjd.database.entity.CarriageType
import java.time.LocalDateTime
import java.util.*

data class TicketResponse (
    val date: LocalDateTime,
    val trainNumber: Long,
    val carriage: Int,
    val place: Int,
    val carriageType: CarriageType,
    val userId: Long,
    val companionId: Long?,
)
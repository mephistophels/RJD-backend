package com.mephistophels.rjd.model.request

import com.mephistophels.rjd.database.entity.CarriageType
import jakarta.persistence.Column
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class TicketRequest(
    val date: LocalDateTime,
    val trainNumber: Long,
    val carriage: Int,
    val place: Int,
    var carriageType: CarriageType,
    val userId: Long,
    val companionId: Long = -1,
    )
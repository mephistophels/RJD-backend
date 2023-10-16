package com.mephistophels.rjd.database.repository

import com.mephistophels.rjd.database.entity.Ticket
import com.mephistophels.rjd.database.entity.user.User
import java.time.LocalDateTime

interface TicketDao : AppRepository<Ticket> {
    fun findAllByUser(user: User): Set<Ticket>
    fun findAllByDateAndTrainNumber(date: LocalDateTime, trainNumber: Long): List<Ticket>
}
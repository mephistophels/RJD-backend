package com.mephistophels.rjd.database.repository

import com.mephistophels.rjd.database.entity.Ticket
import com.mephistophels.rjd.database.entity.user.User

interface TicketDao : AppRepository<Ticket> {

    fun findAllByUser(user: User): Set<Ticket>

}
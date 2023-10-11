package com.mephistophels.rjd.service.impl

import com.mephistophels.rjd.database.entity.Ticket
import com.mephistophels.rjd.database.repository.CompanionDao
import com.mephistophels.rjd.database.repository.TicketDao
import com.mephistophels.rjd.database.repository.UserDao
import com.mephistophels.rjd.mappers.TicketMapper
import com.mephistophels.rjd.model.request.TicketRequest
import com.mephistophels.rjd.model.response.TicketResponse
import com.mephistophels.rjd.service.TicketService
import com.mephistophels.rjd.service.UserService
import org.springframework.stereotype.Service

@Service
class TicketServiceImpl(
    val dao: TicketDao,
    val mapper: TicketMapper,
    val userDao: UserDao,
    val companionDao: CompanionDao,
) : TicketService {
    override fun createTicket(ticketRequest: TicketRequest): TicketResponse {
        val entity = mapper.asEntity(ticketRequest)
        entity.user = userDao.findEntityById(ticketRequest.userId)
        entity.companion = companionDao.findEntityById(ticketRequest.companionId)
        return mapper.asResponse(dao.save(entity))
    }

    override fun createCompanionTicket(ticketRequest: TicketRequest): TicketResponse {
        TODO("Not yet implemented")
    }

    override fun findTicketByUserId(userId: Long): Set<TicketResponse> {
        val res = mutableSetOf<TicketResponse>()
        for (ticket in dao.findAllByUser(userDao.findEntityById(userId))) {
            res.add(mapper.asResponse(ticket))
        }
        return res
    }
}
package com.mephistophels.rjd.service

import com.mephistophels.rjd.database.entity.Ticket
import com.mephistophels.rjd.model.request.RecommendationRequest
import com.mephistophels.rjd.model.request.TicketRequest
import com.mephistophels.rjd.model.response.TicketResponse

interface TicketService {
    fun createTicket(ticketRequest: TicketRequest) : TicketResponse
    fun createCompanionTicket(ticketRequest: TicketRequest) : TicketResponse
    fun findTicketByUserId(userId: Long): Set<TicketResponse>
    fun findAllTickets(request: RecommendationRequest): List<Ticket>
}
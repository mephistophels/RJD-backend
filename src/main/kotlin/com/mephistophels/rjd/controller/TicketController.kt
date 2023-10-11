package com.mephistophels.rjd.controller

import com.mephistophels.rjd.database.entity.Ticket
import com.mephistophels.rjd.model.request.TicketRequest
import com.mephistophels.rjd.model.response.TicketResponse
import com.mephistophels.rjd.service.TicketService
import com.mephistophels.rjd.util.API_VERSION_1
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("$API_VERSION_1/ticket")
class TicketController(
     private val service: TicketService,
) {
    @PostMapping
    fun createTicket(@RequestBody ticketRequest: TicketRequest): TicketResponse{
        return service.createTicket(ticketRequest)
    }

    @GetMapping("/{id}")
    fun findTicketsById(@PathVariable id: Long) : Set<TicketResponse> {
        return service.findTicketByUserId(id)
    }
}
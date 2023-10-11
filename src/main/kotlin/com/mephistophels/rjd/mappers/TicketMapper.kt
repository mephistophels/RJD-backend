package com.mephistophels.rjd.mappers

import com.mephistophels.rjd.database.entity.Ticket
import com.mephistophels.rjd.model.request.TicketRequest
import com.mephistophels.rjd.model.response.TicketResponse
import org.springframework.stereotype.Component

@Component
class TicketMapper {
    fun asEntity(ticketRequest: TicketRequest) : Ticket{
        val entity = Ticket(
            date = ticketRequest.date,
            trainNumber = ticketRequest.trainNumber,
            carriage = ticketRequest.carriage,
            place = ticketRequest.place,
            carriageType = ticketRequest.carriageType
        )
        return entity
    }

    fun asResponse(entity: Ticket): TicketResponse{
        val ticketResponse = TicketResponse(
            date = entity.date,
            trainNumber = entity.trainNumber,
            carriage = entity.carriage,
            place = entity.place,
            carriageType = entity.carriageType,
            userId = entity.user.id,
            companionId = entity.companion?.id ?: -1
        )
        return ticketResponse
    }
}
package com.mephistophels.rjd.recommendation.impl

import com.mephistophels.rjd.database.entity.Ticket
import com.mephistophels.rjd.database.entity.user.User
import com.mephistophels.rjd.mappers.RecommendationMapper
import com.mephistophels.rjd.model.dto.AbstractUserDto
import com.mephistophels.rjd.model.dto.Carriage
import com.mephistophels.rjd.model.dto.Place
import com.mephistophels.rjd.model.dto.Train
import com.mephistophels.rjd.model.request.RecommendationRequest
import com.mephistophels.rjd.recommendation.RecommendationService
import com.mephistophels.rjd.service.TicketService
import com.mephistophels.rjd.service.UserService
import com.mephistophels.rjd.util.getPrincipal
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.math.pow

@Service
@Transactional
class RecommendationServiceImpl(
    private val ticketService: TicketService,
    private val userService: UserService,
    private val mapper: RecommendationMapper
) : RecommendationService {

    override fun getRecommendations(request: RecommendationRequest): Train {
        val tickets = ticketService.findAllTickets(request)
        val train = Train(
            (1..8).map { carriageNum ->
                Carriage((1..36).map { placeNum ->
                    val ticket = tickets.find { it.carriage == carriageNum && it.place == placeNum }
                    if (ticket == null) Place(placeNum, null, 0.0)
                    else {
                        val user = mapper.asAbstractUserDto(if (ticket.companion != null) ticket.companion!! else ticket.user)
                        Place(placeNum, user, 0.0)
                    }
                })
            }
        )
        fillRecommendations(request, train, tickets)
        return train
    }

    private fun fillRecommendations(request: RecommendationRequest, train: Train, tickets: List<Ticket>) {
        val self = userService.findEntityById(getPrincipal())
        val selfDto = mapper.asAbstractUserDto(self)

        val byVector = getByUserVector(train, tickets, selfDto)

        val result = byVector.indices.map { byVector[it] }

        var index = -1
        for (carriage in train.carriages) {
            for (place in carriage.places) {
                index += 1
                if (place.user != null) continue
                place.rating += result[index]
            }
        }
    }

    private fun getByUserVector(train: Train, tickets: List<Ticket>, self: AbstractUserDto): List<Double> {
        val rating = mutableListOf<Double>()
        for (carriage in train.carriages) {
            for (place in carriage.places) {
                rating += if (place.user != null) 0.0
                          else similarity(self, place)
            }
        }
        return normalize(rating)
    }

    private fun getByQuestionnaire(train: Train, tickets: List<Ticket>, self: AbstractUserDto) {
        val questionnaire = MutableList(9) { 1 }
        val result = MutableList(questionnaire.size) { 0.0 }
        for (i in questionnaire.indices) {
            if (i + 1 in listOf(1, 2, 4, 7)) {
                result[i] = questionnaire[i] - 2.0
            }
            if (i + 1 in listOf(5, 6, 8)) {
                result[i] = (if (questionnaire[i] == 1) 1 else questionnaire[i] - 3).toDouble()
            }
            if (i + 1 == 3) {
                result[i] = questionnaire[i] * 2 - 3.0
            }
            if (i + 1 == 9) {
                result[i] = when(questionnaire[i]) {
                    1 -> -1.0
                    2 -> 0.5
                    3 -> 1.0
                    else -> -0.5
                }
            }
        }
    }

    /**
     * Euclidean distance
     */
    private fun similarity(self: AbstractUserDto, place: Place): Double {
        val a = place.user!!.getVector()
        val b = self.getVector()
        val lst = a.mapIndexed {index, it -> (it - b[index]).pow(2.0) }
        return lst.sum() / lst.count()
    }

    /**
     * Each output element in [-1; 1]
     */
    fun normalize(input: MutableList<Double>): List<Double> {
        if (input.isEmpty()) return input
        val min = input.filter { it != 0.0 }.minOrNull() ?: 0.0
        val max = input.filter { it != 0.0 }.maxOrNull() ?: 0.0
        if (min == max) return List(input.size) { 0.0 }
        return input.map { ((it - min) / (max - min) * 2) - 1 }
    }
}
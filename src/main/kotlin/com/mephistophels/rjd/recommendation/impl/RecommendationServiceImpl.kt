package com.mephistophels.rjd.recommendation.impl

import com.mephistophels.rjd.database.entity.Ticket
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
                Carriage(carriageNum, (1..36).map { placeNum ->
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
        val byByQuestionnaire = getByQuestionnaire(train, tickets, selfDto)
        val bySelfQualities = getBySelfQualities(train, tickets, selfDto)
        val result = normalize(byVector.indices.map { byVector[it] * 0.25 + byByQuestionnaire[it] * 2 + bySelfQualities[it] * 1.5 })

        var index = -1
        for (carriage in train.carriages) {
            for (place in carriage.places) {
                index += 1
                if (place.user != null) continue
                place.rating = (result[index] + 1) / 2
            }
        }
    }

    private fun getByUserVector(train: Train, tickets: List<Ticket>, self: AbstractUserDto): List<Double> {
        val rating = mutableListOf<Double>()
        val coupe = mutableListOf<Double>()
        for (carriage in train.carriages) {
            for (place in carriage.places) {
                coupe += if (place.user == null) 0.0
                          else similarity(self.getVector(), place.user!!.getVector())
                if ((place.number - 1) % 4 == 3) {
                    val count = coupe.count { it != 0.0 }
                    val average = if (count == 0) 0.0 else coupe.sum() / count
                    rating += MutableList(4) { average }
                    coupe.clear()
                }
            }
        }
        return rating
        return normalize(rating)
    }

    private fun getByQuestionnaire(train: Train, tickets: List<Ticket>, self: AbstractUserDto): List<Double> {
        val rating = mutableListOf<Double>()
        val coupe = mutableListOf<Double>()
        for (carriage in train.carriages) {
            for (place in carriage.places) {
                coupe += if (place.user == null) 0.0
                          else similarity(self.getQuestionnaireVector(), place.user!!.getQuestionnaireVector())
                if ((place.number - 1) % 4 == 3) {
                    val count = coupe.count { it != 0.0 }
                    val average = if (count == 0) 0.0 else coupe.sum() / count
                    rating += MutableList(4) { average }
                    coupe.clear()
                }
            }
        }
        return rating
        return normalize(rating)
    }

    private fun getBySelfQualities(train: Train, tickets: List<Ticket>, self: AbstractUserDto): List<Double> {
        val rating = mutableListOf<Double>()
        for (carriage in train.carriages) {
            for (place in carriage.places) {
                if ((place.number - 1) % 4 != 0) continue
                var iCanHelp = 0.0
                var smdCanHelpToMe = 0.0
                for (num in getCoupeList(place.number)) {
                    val user = carriage.places[num - 1].user ?: continue
                    if (self.getQuestionnaireVector().last() > 0 && user.isOld()) iCanHelp = self.getQuestionnaireVector().last()
                    if (self.isOld() && user.getQuestionnaireVector().last() > 0) smdCanHelpToMe = user.getQuestionnaireVector().last()
                }
                val delta = if (self.isOld()) 2 else 0
                for (num in getCoupeList(place.number)) {
                    var res = if (place.user == null) 0.0
                              else (iCanHelp + smdCanHelpToMe) * 1.0
                    if (num % 2 == 1) res += delta else res -= delta / 2
                    rating += res
                }
            }
        }
        return rating
        return normalize(rating)
    }

    private fun getCoupeList(place: Int): List<Int> = mutableListOf(
        ((place - 1) / 4) * 4 + 1,
        ((place - 1) / 4) * 4 + 2,
        ((place - 1) / 4) * 4 + 3,
        ((place - 1) / 4) * 4 + 4,
    )

    /**
     * Euclidean distance
     */
    private fun similarity(a: List<Double>, b: List<Double>): Double {
        val lst = a.mapIndexed {index, it -> (it - b[index]).pow(2.0) }
        return lst.sum() / lst.count()
    }

    /**
     * Each output element in [-1; 1]
     */
    fun normalize(input: List<Double>): List<Double> {
        if (input.isEmpty()) return input
        val min = input.filter { it != 0.0 }.minOrNull() ?: 0.0 // .filter { it != 0.0 }
        val max = input.filter { it != 0.0 }.maxOrNull() ?: 0.0
        if (min == max) return List(input.size) { 0.0 }
        return input.map { ((it - min) / (max - min) * 2) - 1 }
    }
}
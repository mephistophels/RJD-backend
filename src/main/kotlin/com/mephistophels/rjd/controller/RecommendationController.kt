package com.mephistophels.rjd.controller

import com.mephistophels.rjd.model.dto.Train
import com.mephistophels.rjd.model.request.RecommendationRequest
import com.mephistophels.rjd.recommendation.RecommendationService
import com.mephistophels.rjd.util.API_VERSION_1
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("$API_VERSION_1/recommendation")
class RecommendationController(
    private val service: RecommendationService
) {

    @GetMapping
    fun getRecommendation(@RequestBody request: RecommendationRequest): Train {
        return service.getRecommendations(request)
    }
}
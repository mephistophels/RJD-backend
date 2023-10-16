package com.mephistophels.rjd.recommendation

import com.mephistophels.rjd.model.dto.Train
import com.mephistophels.rjd.model.request.RecommendationRequest

interface RecommendationService {
    fun getRecommendations(request: RecommendationRequest): Train
}
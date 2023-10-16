package com.mephistophels.rjd.service

import com.mephistophels.rjd.database.entity.user.User
import com.mephistophels.rjd.model.request.MarkRequest
import com.mephistophels.rjd.model.request.common.PageRequest
import com.mephistophels.rjd.model.response.MarkResponse
import com.mephistophels.rjd.model.response.common.PageResponse
import com.mephistophels.rjd.model.response.user.UserMarkResponse

interface MarkService {
    fun getUserMark(user: User): UserMarkResponse
    fun createMark(request: MarkRequest, recipientId: Long): MarkResponse
    fun getUserMark(userId: Long): UserMarkResponse
    fun getMarksList(userId: Long, request: PageRequest): PageResponse<MarkResponse>
    fun getMark(markId: Long): MarkResponse
    fun getUserMarkForRecommendation(user: Any): UserMarkResponse
}
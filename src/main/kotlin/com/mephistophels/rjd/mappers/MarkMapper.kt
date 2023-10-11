package com.mephistophels.rjd.mappers

import com.mephistophels.rjd.database.entity.Mark
import com.mephistophels.rjd.model.request.MarkRequest
import com.mephistophels.rjd.model.response.MarkResponse
import com.mephistophels.rjd.model.response.common.PageResponse
import org.springframework.context.annotation.Lazy
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import kotlin.math.max

@Component
class MarkMapper(
    @Lazy private val userMapper: UserMapper
) {
    fun asEntity(request: MarkRequest): Mark {
        return Mark(
            mark = request.mark,
            message = request.message
        )
    }

    fun asResponse(entity: Mark): MarkResponse {
        return MarkResponse(
            mark = entity.mark,
            message = entity.message,
            sender = userMapper.asUserShortResponse(entity.sender)
        )
    }

    fun asPageResponse(page: Page<Mark>): PageResponse<MarkResponse> {
        return PageResponse.build(page, ::asResponse)
    }
}
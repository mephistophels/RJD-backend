package com.mephistophels.rjd.service.impl

import com.mephistophels.rjd.database.entity.Mark
import com.mephistophels.rjd.database.entity.user.Companion
import com.mephistophels.rjd.database.entity.user.User
import com.mephistophels.rjd.database.repository.MarkDao
import com.mephistophels.rjd.errors.ResourceNotFoundException
import com.mephistophels.rjd.mappers.MarkMapper
import com.mephistophels.rjd.model.request.MarkRequest
import com.mephistophels.rjd.model.request.common.PageRequest
import com.mephistophels.rjd.model.response.MarkResponse
import com.mephistophels.rjd.model.response.common.PageResponse
import com.mephistophels.rjd.model.response.user.UserMarkResponse
import com.mephistophels.rjd.service.MarkService
import com.mephistophels.rjd.service.UserService
import com.mephistophels.rjd.util.getPrincipal
import jakarta.transaction.Transactional
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service

@Service
@Transactional
class MarkServiceImpl(
    private val mapper: MarkMapper,
    private val dao: MarkDao,
    @Lazy private val userService: UserService
) : MarkService {

    private fun findEntityById(id: Long): Mark {
        return dao.findById(id).orElseThrow { ResourceNotFoundException(id, User::class.java) }
    }

    override fun getMark(markId: Long): MarkResponse {
        val mark = findEntityById(markId)
        return mapper.asResponse(mark)
    }

    override fun createMark(request: MarkRequest, recipientId: Long): MarkResponse {
        val recipient = userService.findUserOrCompanionById(recipientId)
        val sender = userService.findEntityById(getPrincipal())
        val entity = mapper.asEntity(request).apply {
            this.sender = sender
            if (recipient is User) this.recipient = recipient
            else                   this.companion = recipient as? Companion
        }.also { dao.save(it) }
        return mapper.asResponse(entity)
    }

    override fun getMarksList(userId: Long, request: PageRequest): PageResponse<MarkResponse> {
        val page = dao.findAllByRecipientIdAndCompanionId(userId, request.pageable)
        return mapper.asPageResponse(page)
    }

    override fun getUserMark(user: User): UserMarkResponse {
        val count = dao.countAllByRecipientIdAndCompanionId(user.id)
        val mark = if (count > 0) dao.getUserMark(user.id) else 0.0
        return UserMarkResponse(mark = mark, count = count)
    }

    override fun getUserMark(userId: Long): UserMarkResponse {
        val user = userService.findEntityById(userId)
        return getUserMark(user)
    }
}
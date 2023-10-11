package com.mephistophels.rjd.controller

import com.mephistophels.rjd.model.request.MarkRequest
import com.mephistophels.rjd.model.request.common.PageRequest
import com.mephistophels.rjd.model.response.MarkResponse
import com.mephistophels.rjd.model.response.common.PageResponse
import com.mephistophels.rjd.model.response.user.UserMarkResponse
import com.mephistophels.rjd.service.MarkService
import com.mephistophels.rjd.util.API_VERSION_1
import com.mephistophels.rjd.util.getPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$API_VERSION_1/mark")
class MarkController(
    private val service: MarkService
) {
    @GetMapping("/{markId}")
    fun getMark(@PathVariable markId: Long): MarkResponse {
        return service.getMark(markId)
    }

    @PostMapping("/user/{userId}")
    fun createMark(@RequestBody request: MarkRequest, @PathVariable userId: Long): MarkResponse {
        return service.createMark(request, userId)
    }

    @GetMapping("/user/{userId}")
    fun getUserMark(@PathVariable userId: Long): UserMarkResponse {
        return service.getUserMark(userId)
    }

    @GetMapping("/user/me")
    fun getMark(): UserMarkResponse {
        return service.getUserMark(getPrincipal())
    }

    @GetMapping("/user/{userId}/list")
    fun getSelfMarks(@RequestBody request: PageRequest, @PathVariable userId: Long): PageResponse<MarkResponse> {
        return service.getMarksList(userId, request)
    }

    @GetMapping("/user/me/list")
    fun getSelfMarks(request: PageRequest): PageResponse<MarkResponse> {
        return service.getMarksList(getPrincipal(), request)
    }
}
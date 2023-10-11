package com.mephistophels.rjd.controller

import com.mephistophels.rjd.model.request.common.PageRequest
import com.mephistophels.rjd.model.request.user.CompanionRequest
import com.mephistophels.rjd.model.response.common.PageResponse
import com.mephistophels.rjd.model.response.user.CompanionResponse
import com.mephistophels.rjd.model.response.user.UserFullResponse
import com.mephistophels.rjd.model.response.user.UserMediumResponse
import com.mephistophels.rjd.service.UserService
import com.mephistophels.rjd.util.API_VERSION_1
import com.mephistophels.rjd.util.getPrincipal
import jakarta.servlet.http.Part
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$API_VERSION_1/user")
class UserController(
    private val service: UserService
) {

    @GetMapping("/me")
    fun getSelfProfile(): UserFullResponse {
        return service.getSelfProfile(getPrincipal())
    }

    @GetMapping("/{userId}")
    fun getUserProfile(@PathVariable userId: Long): UserMediumResponse {
        return service.getUserProfile(userId)
    }

    /**
     * Companion API
     **/

    @GetMapping("/companion/list")
    fun getAllCompanions(request: PageRequest): PageResponse<CompanionResponse> {
        return service.getCompanionsList(request)
    }

    @PostMapping("/companion")
    fun createCompanion(request: CompanionRequest): CompanionResponse {
        return service.createCompanion(request)
    }

    @PostMapping("/companion/{id}/photo")
    fun uploadCompanionPhoto(@PathVariable id: Long, @RequestPart("photo") photo: Part): CompanionResponse {
        return service.uploadCompanionPhoto(id, photo)
    }

    @DeleteMapping("/companion/{id}")
    fun uploadCompanionPhoto(@PathVariable id: Long): CompanionResponse {
        return service.deleteCompanion(id)
    }
}
package com.mephistophels.rjd.model.request.common

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.RequestParam

class PageRequest(
    @RequestParam val page: Int = 1,
    @RequestParam val size: Int = 10,
    @RequestParam val order: Sort.Direction = Sort.Direction.DESC,
    @RequestParam var field: String = "createdAt",
) {
    var pageable = PageRequest.of(page - 1, size, order, field)

    fun updateRequest(orders: List<Sort.Order>) {
        val sort = Sort.by(orders)
        pageable = PageRequest.of(page - 1, size, sort)
    }
}
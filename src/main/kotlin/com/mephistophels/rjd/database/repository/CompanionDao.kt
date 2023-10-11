package com.mephistophels.rjd.database.repository

import com.mephistophels.rjd.database.entity.Companion
import com.mephistophels.rjd.database.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface CompanionDao : AppRepository<Companion>, PagingAndSortingRepository<Companion, Long> {
    fun findAllByHolder(holder: User, pageable: Pageable): Page<Companion>
}
package com.mephistophels.rjd.database.repository

import com.mephistophels.rjd.database.entity.user.Companion
import com.mephistophels.rjd.database.entity.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface CompanionDao : PagingAndSortingRepository<Companion, Long>, CrudRepository<Companion, Long> {
    fun findAllByHolder(holder: User, pageable: Pageable): Page<Companion>
}
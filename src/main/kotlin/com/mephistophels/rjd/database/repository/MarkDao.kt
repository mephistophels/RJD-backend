package com.mephistophels.rjd.database.repository

import com.mephistophels.rjd.database.entity.Mark
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface MarkDao : AppRepository<Mark>, PagingAndSortingRepository<Mark, Long> {
    @Query("SELECT count(*) FROM Mark m WHERE m.companion.id = ?1 or m.recipient.id = ?1")
    fun countAllByRecipientIdAndCompanionId(recipientId: Long): Int

    @Query("SELECT sum(m.mark)/count(m) FROM Mark m WHERE m.companion.id = ?1 or m.recipient.id = ?1")
    fun getUserMark(recipientId: Long): Double

    @Query("SELECT m FROM Mark m WHERE m.companion.id = ?1 or m.recipient.id = ?1")
    fun findAllByRecipientIdAndCompanionId(recipientId: Long, pageable: Pageable): Page<Mark>
}
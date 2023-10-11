package com.mephistophels.rjd.database.repository

import com.mephistophels.rjd.database.entity.user.User
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserDao : PagingAndSortingRepository<User, Long>, CrudRepository<User, Long> {
    fun findByEmail(email: String): Optional<User>

    fun existsByEmail(email: String): Boolean
}
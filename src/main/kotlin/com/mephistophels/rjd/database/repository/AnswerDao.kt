package com.mephistophels.rjd.database.repository

import com.mephistophels.rjd.database.entity.user.Answer
import com.mephistophels.rjd.database.entity.user.User

interface AnswerDao : AppRepository<Answer> {

    fun findAllByUser(user: User) : List<Answer>
}
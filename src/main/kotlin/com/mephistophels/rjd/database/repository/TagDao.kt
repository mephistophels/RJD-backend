package com.mephistophels.rjd.database.repository

import com.mephistophels.rjd.database.entity.Tag
import com.mephistophels.rjd.database.entity.User

interface TagDao : AppRepository<Tag> {
    fun findAllByUser(user: User) : Set <Tag>
}
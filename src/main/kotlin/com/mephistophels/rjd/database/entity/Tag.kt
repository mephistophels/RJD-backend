package com.mephistophels.rjd.database.entity

import com.mephistophels.rjd.database.entity.common.AbstractCreatedAtEntity
import com.mephistophels.rjd.database.entity.user.Companion
import com.mephistophels.rjd.database.entity.user.User
import jakarta.persistence.*

@Entity
@Table(name = "TagTable")
class Tag (
    @Column(name = "tag", nullable = false)
    var tag: String,
) : AbstractCreatedAtEntity() {

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "userId")
    lateinit var user: User

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "companionId")
    lateinit var companion: Companion
}
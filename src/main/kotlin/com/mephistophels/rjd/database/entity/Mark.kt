package com.mephistophels.rjd.database.entity

import com.mephistophels.rjd.database.entity.common.AbstractCreatedAtEntity
import com.mephistophels.rjd.database.entity.user.Companion
import com.mephistophels.rjd.database.entity.user.User
import jakarta.persistence.*

@Entity
@Table(name = "MarkTable")
class Mark (

    @Column(name = "mark", nullable = false)
    var mark: Int,

    @Column(name = "message", nullable = true)
    var message: String,
) : AbstractCreatedAtEntity() {

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "recipientId")
    var recipient: User? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "companionId")
    var companion: Companion? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "senderId")
    lateinit var sender: User
}
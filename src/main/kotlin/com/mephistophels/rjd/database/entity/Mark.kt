package com.mephistophels.rjd.database.entity

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
    lateinit var recipient: User

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "companionId")
    lateinit var companion: Companion

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "senderId")
    lateinit var sender: User

}
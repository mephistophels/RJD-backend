package com.mephistophels.rjd.database.entity

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
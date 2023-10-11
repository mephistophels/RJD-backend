package com.mephistophels.rjd.database.entity

import com.mephistophels.rjd.database.entity.common.AbstractCreatedAtEntity
import com.mephistophels.rjd.database.entity.user.Companion
import com.mephistophels.rjd.database.entity.user.User
import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "TicketTable")
class Ticket(

    @Column(name = "date", nullable = false)
    val date: Date,

    @Column(name = "trainNumber", nullable = false)
    val trainNumber: Long,

    @Column(name = "carriage", nullable = false)
    val carriage: Int,

    @Column(name = "place", nullable = false)
    val place: Int,

    @Column(name = "carriageType", nullable = false)
    val carriageType: CarriageType,

) : AbstractCreatedAtEntity() {

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "userId")
    lateinit var user: User

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "companionId")
    lateinit var companion: Companion
}
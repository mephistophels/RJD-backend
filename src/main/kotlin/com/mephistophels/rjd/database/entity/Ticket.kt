package com.mephistophels.rjd.database.entity

import com.mephistophels.rjd.database.entity.common.AbstractCreatedAtEntity
import com.mephistophels.rjd.database.entity.user.Companion
import com.mephistophels.rjd.database.entity.user.User
import com.mephistophels.rjd.model.enums.PhotoPath
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.Date

@Entity
@Table(name = "TicketTable")
class Ticket(

    @Column(name = "date", nullable = false)
    val date: LocalDateTime,

    @Column(name = "trainNumber", nullable = false)
    val trainNumber: Long,

    @Column(name = "carriage", nullable = false)
    val carriage: Int,

    @Column(name = "place", nullable = false)
    val place: Int,

    @Column(name = "carriageType", nullable = false)
    val carriageType: CarriageType,

    ) : AbstractCreatedAtEntity() {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId")
    lateinit var user: User

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "companionId")
    var companion: Companion? = null
}
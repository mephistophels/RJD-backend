package com.mephistophels.rjd.database.entity.user

import com.mephistophels.rjd.database.entity.common.AbstractCreatedAtEntity
import jakarta.persistence.*

@Entity
@Table(name = "AnswerTable")
class Answer(

    @Column(name = "question")
    val question: Int,

    @Column(name = "answer")
    val answer: Int,

) : AbstractCreatedAtEntity() {

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "userId")
    lateinit var user: User

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "companionId")
    lateinit var companion: Companion
}
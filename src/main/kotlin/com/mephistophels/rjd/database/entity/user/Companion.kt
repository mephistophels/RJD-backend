package com.mephistophels.rjd.database.entity.user

import com.mephistophels.rjd.database.entity.Mark
import com.mephistophels.rjd.database.entity.Tag
import com.mephistophels.rjd.database.entity.Ticket
import com.mephistophels.rjd.database.entity.common.AbstractUserEntity
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "CompanionTable")
class Companion(

    @Column(name = "phone")
    var phone: String,

    @Column(name = "avatar")
    var avatar:String,

    @Column(name = "surname", nullable = false)
    var surname: String,

    @Column(name = "sex", nullable = true)
    var sex: String, // male||female

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "patronymic", nullable = true)
    var patronymic: String? = null,

    @Column(name = "birthday", nullable = true)
    var birthday: LocalDate,

    @Column(name = "bio", nullable = true)
    var bio: String? = null,
): AbstractUserEntity() {

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "holderId")
    lateinit var holder: User

    @OneToMany(mappedBy = "companion", cascade = [CascadeType.ALL])
    var tag: Set<Tag> = HashSet()

    @OneToMany(mappedBy = "companion", cascade = [CascadeType.ALL])
    var mark: Set<Mark> = HashSet()

    @OneToMany(mappedBy = "companion", cascade = [CascadeType.ALL])
    var tickets: Set<Ticket> = HashSet()

    @OneToMany(mappedBy = "companion", cascade = [CascadeType.ALL])
    val answers: Set<Answer> = HashSet()

}
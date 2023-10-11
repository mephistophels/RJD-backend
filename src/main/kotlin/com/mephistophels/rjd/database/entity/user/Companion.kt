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
    @Column(name = "surname", nullable = false)
    var surname: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "patronymic", nullable = true)
    var patronymic: String? = null,

    @Column(name = "birthday", nullable = false)
    var birthday: LocalDate,

    @Column(name = "phone", nullable = true)
    var phone: String? = null,

    @Column(name = "bio", nullable = true)
    var bio: String? = null,

    @Column(name = "avatar", nullable = true)
    var avatar: String? = null

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
}
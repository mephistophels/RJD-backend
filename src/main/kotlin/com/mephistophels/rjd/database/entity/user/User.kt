package com.mephistophels.rjd.database.entity.user

import com.mephistophels.rjd.database.entity.Mark
import com.mephistophels.rjd.database.entity.Tag
import com.mephistophels.rjd.database.entity.Ticket
import com.mephistophels.rjd.database.entity.common.AbstractUserEntity
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "UserTable")
class User(
    @Column(name = "email", nullable = false, unique = true)
    var email: String,

    @Column(name = "phone", nullable = false)
    var phone: String,

    @Column(name = "avatar", nullable = true)
    var avatar:String?,

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

    @Column(name = "hash", nullable = false)
    var hash: String? = null

    @OneToMany(mappedBy = "recipient", cascade = [CascadeType.ALL])
    var receivedMark: Set<Mark> = HashSet<Mark>()

    @OneToMany(mappedBy = "sender", cascade = [CascadeType.ALL])
    var sentMark: Set<Mark> = HashSet<Mark>()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    var tag: Set<Tag> = HashSet<Tag>()

    @OneToMany(mappedBy = "holder", cascade = [CascadeType.ALL])
    var companions: Set<Companion> = HashSet()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    var tickets: Set<Ticket> = HashSet()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    var answers: List<Answer> = ArrayList()
}
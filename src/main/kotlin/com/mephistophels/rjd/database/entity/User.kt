package com.mephistophels.rjd.database.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "UserTable")
class User(
    @Column(name = "email", nullable = false, unique = true)
    var email: String,

    @Column(name = "phone", nullable = false)
    var phone: String,

    @Column(name = "avatar")
    var avatar:String,

    @Column(name = "surname", nullable = false)
    var surname: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "patronymic", nullable = true)
    var patronymic: String? = null,

    @Column(name = "birthday", nullable = true)
    var birthday: LocalDate? = null,

    @Column(name = "bio", nullable = true)
    var bio: String? = null,

) : AbstractCreatedAtEntity() {

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
}
package com.mephistophels.rjd.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "UserTable")
class User(
    @Column(name = "email", nullable = false, unique = true)
    var email: String,

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

    @Column(name = "imageLink", nullable = true)
    var link:String? = null

) : AbstractCreatedAtEntity() {

    @Column(name = "hash", nullable = false)
    var hash: String? = null
}
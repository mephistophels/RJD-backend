package com.mephistophels.rjd.database.entity.common

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class AbstractUserEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(generator="user_seq")
    @SequenceGenerator(name="user_seq",sequenceName="USER_SEQ", allocationSize=1)
    var id: Long = 0

    @CreationTimestamp
    @Column(nullable = false)
    lateinit var createdAt: LocalDateTime
}
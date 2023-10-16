package com.mephistophels.rjd.model.dto

import com.mephistophels.rjd.database.entity.Tag
import com.mephistophels.rjd.model.response.MarkResponse
import com.mephistophels.rjd.model.response.user.UserMarkResponse
import jakarta.persistence.Column
import java.time.LocalDate
import java.time.temporal.TemporalAmount
import kotlin.math.sqrt

class AbstractUserDto(
    var email: String? = null,
    var phone: String? = null,
    var avatar: String? = null,
    var surname: String,
    var name: String,
    var patronymic: String? = null,
    var birthday: LocalDate,
    var tag: Set<Tag> = HashSet(),
    var mark: UserMarkResponse
) {
    fun getVector(): List<Double> {
        val vector = mutableListOf<Double>()
        vector += (LocalDate.now().year - birthday.year) / 100 * sqrt(4.0)
        vector += mark.mark / 5
//        vector += 0
        return vector
    }

    fun isOld(): Boolean {
        return LocalDate.now().year - birthday.year >= 50
    }
}
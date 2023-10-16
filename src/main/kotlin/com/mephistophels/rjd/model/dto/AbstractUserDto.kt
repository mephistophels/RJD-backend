package com.mephistophels.rjd.model.dto

import com.mephistophels.rjd.database.entity.Tag
import com.mephistophels.rjd.model.response.user.UserMarkResponse
import java.time.LocalDate

class AbstractUserDto(
    var email: String? = null,
    var phone: String? = null,
    var avatar: String? = null,
    var surname: String,
    var name: String,
    var sex: String,
    var patronymic: String? = null,
    var birthday: LocalDate,
    var tag: Set<Tag> = HashSet(),
    var mark: UserMarkResponse,
    var questionnaire: List<Int>
) {
    fun getVector(): List<Double> {
        val vector = mutableListOf<Double>()
        vector += (if (sex == "male") 1 else -1) * 2.5
        vector += (LocalDate.now().year - birthday.year) / 100 * 2.0
        vector += mark.mark / 5
//        vector += 0
        return vector
    }

    fun getQuestionnaireVector(): MutableList<Double> {
        val result = MutableList(questionnaire.size) { 0.0 }
        for (i in questionnaire.indices) {
            if (i + 1 in listOf(1, 2, 4, 7)) {
                result[i] = questionnaire[i] - 2.0
            }
            if (i + 1 in listOf(5, 6, 8)) {
                result[i] = (if (questionnaire[i] == 1) 1 else questionnaire[i] - 3).toDouble()
            }
            if (i + 1 == 3) {
                result[i] = questionnaire[i] * 2 - 3.0
            }
            if (i + 1 == 9) {
                result[i] = when(questionnaire[i]) {
                    1 -> -1.0
                    2 -> 0.5
                    3 -> 1.0
                    else -> -0.5
                }
            }
        }
        return result
    }

    fun isOld(): Boolean {
        return LocalDate.now().year - birthday.year >= 50
    }
}
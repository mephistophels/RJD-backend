package com.mephistophels.rjd.mappers

import com.mephistophels.rjd.database.entity.user.Companion
import com.mephistophels.rjd.database.entity.user.User
import com.mephistophels.rjd.model.dto.AbstractUserDto
import com.mephistophels.rjd.service.MarkService
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Component
class RecommendationMapper(
    @Lazy private val markService: MarkService
) {
    fun asAbstractUserDto(user: Any): AbstractUserDto {
        return when(user) {
            is User -> AbstractUserDto(
                email = user.email,
                phone = user.phone,
                avatar = user.avatar,
                surname = user.surname,
                name = user.name,
                patronymic = user.patronymic,
                sex = user.sex,
                birthday = user.birthday,
                tag = user.tag.map { it.tag }.toMutableSet(),
                mark = markService.getUserMarkForRecommendation(user),
                questionnaire = user.answers.map { it.answer }
            )
            is Companion -> AbstractUserDto(
                email = null,
                phone = user.phone,
                avatar = user.avatar,
                surname = user.surname,
                name = user.name,
                sex = user.sex,
                patronymic = user.patronymic,
                birthday = user.birthday,
                tag = user.tag.map { it.tag }.toMutableSet(),
                mark = markService.getUserMarkForRecommendation(user),
                questionnaire = user.answers.map { it.answer }
            )
            else -> TODO("Не User и не Companion")
        }
    }
}
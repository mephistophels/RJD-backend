package com.mephistophels.rjd.model.response


import com.mephistophels.rjd.model.response.user.UserShortResponse

class MarkResponse(
    val mark: Int,
    val message: String,
    val sender: UserShortResponse,
    val recipient: UserShortResponse? = null,
    val companion: UserShortResponse? = null
)
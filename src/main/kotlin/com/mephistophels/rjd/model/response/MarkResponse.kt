package com.mephistophels.rjd.model.response

import com.mephistophels.rjd.database.entity.MarkRecipient

data class MarkResponse(
    val mark: Int,
    val message: String,
    val recipient: MarkRecipient,
    val customerId: Long,
    val executorId: Long,
    val orderId: Long,
)
package com.mephistophels.rjd.errors

import org.springframework.http.HttpStatus
import java.lang.reflect.Type
import kotlin.reflect.KClass

class ResourceNotFoundException(
    data: Any? = null,
    type: Type? = null
) : ApiError(
    status = HttpStatus.NOT_FOUND,
    message = "Такого ресурса не существует" + if (data == null) "" else " ${if (type != null) (type as Class<*>).simpleName else ""} [$data]",
    debugMessage = "Resource not found" + if (data == null) "" else " [$data]"
)
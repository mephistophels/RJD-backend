package com.mephistophels.rjd.errors

import org.springframework.http.HttpStatus

class UnsupportedFileTypeException : ApiError(
    status = HttpStatus.UNSUPPORTED_MEDIA_TYPE,
    message = "Неподдерживаемый формат файла"
)
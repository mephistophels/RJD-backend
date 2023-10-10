package com.mephistophels.rjd.model.enums

enum class PhotoPath(val path: String) {
    USER("/users");
    companion object {
        fun parse(path: String) = PhotoPath.values().find { it.path == "/$path" }
    }
}

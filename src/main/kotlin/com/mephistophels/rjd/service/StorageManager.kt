package com.mephistophels.rjd.service

import com.mephistophels.rjd.model.enums.PhotoPath
import jakarta.servlet.http.Part
import org.springframework.http.MediaType
import java.io.File
import java.nio.file.Path

interface StorageManager {
    val root: Path
    fun saveImage(part: Part, dir: PhotoPath, oldPath: String?): String
    fun getPhotoFile(path: String): File
    fun getPhotoPath(path: String): String
    fun removeImage(path: String?, dir: PhotoPath): Boolean
    fun checkAllowedExtension(file: File): MediaType
}
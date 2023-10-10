package com.mephistophels.rjd.service.impl

import com.mephistophels.rjd.errors.ApiError
import com.mephistophels.rjd.errors.UnsupportedFileTypeException
import com.mephistophels.rjd.model.enums.PhotoPath
import com.mephistophels.rjd.service.StorageManager
import com.mephistophels.rjd.util.API_PUBLIC
import com.mephistophels.rjd.util.API_VERSION_1
import com.mephistophels.rjd.util.createIfNotExist


import org.apache.tika.Tika
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.util.*
import kotlin.io.path.*
import jakarta.servlet.http.Part
import jakarta.transaction.Transactional

@Service
class StorageManagerImpl(
    @Value("\${values.baseUrl}")
    private val baseUrl: String,
) : StorageManager {
    override val root = Path("uploads").also { if (it.notExists()) Files.createDirectory(it) }

    override fun saveImage(part: Part, dir: PhotoPath, oldPath: String?, isAdmin: Boolean): String {
        val extension = checkMime(part.inputStream)
        val filename = "${UUID.randomUUID()}.$extension"

        val path = Path(root.pathString + dir.path).createIfNotExist()
        val newPath: String
        path.resolve(filename).also {
            Files.copy(part.inputStream, it)
            newPath = "${it.parent.name}/${it.name}"
        }

        if (oldPath != null) {
            removeImage(oldPath, dir)
        }

        return newPath
    }

    override fun getPhotoFile(path: String): File {
        if (!isPathCorrect(path)) {
            throw ApiError(HttpStatus.BAD_REQUEST, "Некорректный путь")
        }
        return File("$root$path")

    }

    /**
     * Check cases when path contains:
     * - more than 2 dots: users/uuid.medium..jpg
     * - 2 dots in a row users/uuid..jpg
     */
    fun isPathCorrect(path: String): Boolean {
        return !(
            path.count { it == '.' } == 0 || path.count { it == '.' } > 2 ||
                (path.count { it == '.' } == 2 && path.contains(".."))
            )
    }

    override fun getPhotoPath(path: String): String = "http://$baseUrl$API_PUBLIC/uploads/$path"

    override fun removeImage(path: String?, dir: PhotoPath): Boolean {
        if (path == null) return false
        val largeFile = File("${root.name}/$path")
        return root.resolve(largeFile.absolutePath).deleteIfExists()
    }

    override fun checkAllowedExtension(file: File): MediaType {
        return when (file.extension) {
            "png" -> MediaType.IMAGE_PNG
            "jpg", "jpeg" -> MediaType.IMAGE_JPEG
            "gif" -> MediaType.IMAGE_GIF
            else -> throw UnsupportedFileTypeException()
        }
    }

    private fun checkMime(stream: InputStream): String {
        val (mime, ext) = Tika().detect(stream).split("/")
        if (mime != "image") {
            throw UnsupportedFileTypeException()
        }
        return ext
    }
}

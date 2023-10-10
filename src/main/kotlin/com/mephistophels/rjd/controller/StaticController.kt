package com.mephistophels.rjd.controller

import com.mephistophels.rjd.errors.ResourceNotFoundException
import com.mephistophels.rjd.service.StorageManager
import com.mephistophels.rjd.util.API_PUBLIC
import org.springframework.core.io.InputStreamResource
import org.springframework.http.ContentDisposition
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.util.*

@RestController
@RequestMapping("$API_PUBLIC/upload")
class StaticController(
    private val manager: StorageManager
) {
    @GetMapping("/{dir}/{fileName}")
    fun getPhoto(@PathVariable dir: String, @PathVariable fileName: String): ResponseEntity<InputStreamResource> {
        val file = manager.getPhotoFile("$dir/$fileName")
        return getPhotoResponse(file)
    }

    private fun getPhotoResponse(file: File): ResponseEntity<InputStreamResource> {
        val contentType = manager.checkAllowedExtension(file)
        return if (file.exists()) {
            return ResponseEntity.ok()
                .contentType(contentType)
                .contentLength(file.length())
                .body(InputStreamResource(file.inputStream()))
        } else {
            throw ResourceNotFoundException(File::class.java)
        }
    }
}
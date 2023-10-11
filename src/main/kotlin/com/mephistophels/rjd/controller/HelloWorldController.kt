package com.mephistophels.rjd.controller

import com.mephistophels.rjd.database.repository.CompanionDao
import com.mephistophels.rjd.database.repository.UserDao
import com.mephistophels.rjd.util.API_PUBLIC
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(API_PUBLIC)
class HelloWorldController(
    private val dao1: UserDao,
    private val dao2: CompanionDao,
) {

    @GetMapping("/hello")
    fun getHelloWorld(): String {
        return "Hello, world!"
    }
}
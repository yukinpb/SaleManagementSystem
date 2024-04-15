package com.example.userservice.controller

import com.example.userservice.model.User
import com.example.userservice.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/user")
@CrossOrigin
class UserController(
        private val userService: UserService
) {
    @GetMapping("/login")
    fun login(@RequestParam username: String, @RequestParam password: String): ResponseEntity<User> {
        val user = userService.login(username, password)
        return if(user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/{userId}")
    fun getUserById(@PathVariable userId: Int): ResponseEntity<User> {
        val user = userService.getUserById(userId)
        return if(user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}

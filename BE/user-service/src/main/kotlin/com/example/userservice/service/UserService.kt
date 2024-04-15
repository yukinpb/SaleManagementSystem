package com.example.userservice.service

import com.example.userservice.model.User
import com.example.userservice.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
        private val userRepository: UserRepository
) {
    fun getUserById(userId: Int): User? = userRepository.findById(userId).get()

    fun login(username: String, password: String): User? {
        return userRepository.findByUsernameAndPassword(username, password)
    }
}
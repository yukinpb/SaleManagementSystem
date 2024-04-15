package com.example.userservice.repository

import com.example.userservice.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Int> {
    fun findByUsernameAndPassword(username: String, password: String): User?
}
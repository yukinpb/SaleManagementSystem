package com.example.userservice.model

import jakarta.persistence.Entity

@Entity
data class User(
    var username: String = "",
    var password: String = "",
    var position: String = ""
): Person()


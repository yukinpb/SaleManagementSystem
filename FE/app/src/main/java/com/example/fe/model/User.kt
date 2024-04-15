package com.example.fe.model

data class User(
    var username: String = "",
    var password: String = "",
    var position: String = ""
): Person()


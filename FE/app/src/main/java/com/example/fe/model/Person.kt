package com.example.fe.model
import java.io.Serializable
import java.sql.Date

open class Person(
    open val id: Int = 0,
    open var name: String = "",
    open var dateOfBirth: Date = Date(0),
    open var gender: String = "",
    open var streetAddress: String = "",
    open var city: String = "",
    open var country: String = "",
    open var phoneNumber: String = "",
    open var email: String = "",
): Serializable
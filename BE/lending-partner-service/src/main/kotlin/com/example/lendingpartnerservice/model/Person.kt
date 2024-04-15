package com.example.lendingpartnerservice.model

import jakarta.persistence.*
import java.sql.Date

@Entity(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
open class Person(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        open val id: Int = 0,
        open var name: String = "",
        open var dateOfBirth: Date = Date(0),
        open var gender: String = "",
        open var streetAddress: String = "",
        open var city: String = "",
        open var country: String = "",
        open var phoneNumber: String = "",
        open var email: String = "",
)
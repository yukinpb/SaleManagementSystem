package com.example.fe.designpattern.command

import com.example.fe.model.Product

interface Command {
    suspend fun execute()
    suspend fun undo()
}
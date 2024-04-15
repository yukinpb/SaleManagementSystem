package com.example.fe.designpattern.command

import com.example.fe.model.Product

class UndoCommand(
    private val command: Command
): Command {
    override suspend fun execute() {
        command.execute()
    }

    override suspend fun undo() {
        command.undo()
    }
}
package dev.tarna.skraps.api.util

import org.bukkit.command.CommandSender

fun CommandSender.send(message: String) {
    this.sendMessage(mm.deserialize(message))
}
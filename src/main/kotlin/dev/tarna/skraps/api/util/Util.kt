package dev.tarna.skraps.api.util

import org.bukkit.Bukkit

object Util {
    private const val PREFIX = "<gray>[<red>Skraps<gray>] "

    /**
     * Log a colored message to the console
     * @param messages List of messages to log
     */
    fun log(vararg messages: String) {
        for (message in messages) {
            Bukkit.getConsoleSender().sendMessage(mm.deserialize(PREFIX + message))
        }
    }

    val debugs = mutableListOf<String>()

    /**
     * Log a message to the console and add it to the debug list for later use
     *
     * System inspired from SkBee Util class
     * @see <a href="https://github.com/ShaneBeee/SkBee/blob/master/src/main/java/com/shanebeestudios/skbee/api/util/Util.java#L73">Util.java#L73</a>
     * @param message The message to log
     */
    fun logLoading(message: String) {
        debugs.add(message)
        log(message)
    }
}
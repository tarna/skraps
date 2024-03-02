package dev.tarna.skraps.api.command

import ch.njol.skript.Skript
import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.HelpCommand
import co.aikar.commands.annotation.Subcommand
import dev.tarna.skraps.Skraps
import dev.tarna.skraps.api.config.config
import dev.tarna.skraps.api.util.Util
import dev.tarna.skraps.api.util.send
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

@CommandAlias("skraps")
@CommandPermission("skraps.admin")
class SkrapsCommand(private val plugin: Skraps) : BaseCommand() {
    @Subcommand("info")
    fun skraps(sender: CommandSender) {
        sender.send("<gray>--- [<red>Skraps Loading Info<gray>] ---")
        Util.debugs.forEach { sender.send("- <gray>$it") }
        sender.send("<gray>--- [<red>Server Info<gray>] ---")
        sender.send("<gray>Server Version: <red>${Bukkit.getVersion()}")
        sender.send("<gray>Skript Version: <red>${Skript.getVersion()}")
        sender.send("<gray>Skript Addons:")
        Skript.getAddons().forEach {
            val name = it.name
            if (!name.contains("Skraps")) {
                sender.send("- <gray>$name <red>v${it.plugin.description.version}")
            }
        }
        sender.send("<gray>Skraps Version: <red>${plugin.description.version}")
        sender.send("<gray>Skraps Website: <red>${plugin.description.website}")
    }

    @Subcommand("reload")
    fun reload(sender: CommandSender) {
        config.reload()
        sender.send("<green>Skraps has been reloaded!")
    }

    @HelpCommand
    fun help(sender: CommandSender) {
        sender.send("<gray>--- [<red>Skraps Help<gray>] ---")
        sender.send("<gray>/skraps info - <red>Info about Skraps")
        sender.send("<gray>/skraps reload - <red>Reload the Skraps config")
    }
}
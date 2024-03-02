package dev.tarna.skraps

import co.aikar.commands.PaperCommandManager
import dev.tarna.skraps.api.command.SkrapsCommand
import org.bukkit.plugin.java.JavaPlugin

class Skraps : JavaPlugin() {
    companion object {
        fun get() = getPlugin(Skraps::class.java)

        lateinit var addonLoader: AddonLoader
        lateinit var commandManager: PaperCommandManager
    }

    override fun onEnable() {
        val now = System.currentTimeMillis()

        addonLoader = AddonLoader(this)
        if (!addonLoader.canLoad()) {
            server.pluginManager.disablePlugin(this)
            return
        }

        registerCommands()

        logger.info("Skraps has been enabled in ${System.currentTimeMillis() - now}ms!")
    }

    override fun onDisable() {
        logger.info("Skraps has been disabled!")
    }

    private fun registerCommands() {
        commandManager = PaperCommandManager(this)
        commandManager.registerCommand(SkrapsCommand(this))
    }
}
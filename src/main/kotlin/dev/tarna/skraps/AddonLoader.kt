package dev.tarna.skraps

import ch.njol.skript.Skript
import ch.njol.skript.SkriptAddon
import ch.njol.skript.util.Version
import dev.tarna.skraps.api.config.config
import dev.tarna.skraps.api.util.SkriptUtils
import dev.tarna.skraps.api.util.Util
import java.io.IOException

/**
 * Addon loader class inspired from SkBee
 * @see <a href="https://github.com/ShaneBeee/SkBee/blob/master/src/main/java/com/shanebeestudios/skbee/AddonLoader.java">AddonLoader.java</a>
 */
class AddonLoader(val plugin: Skraps) {
    private val pm = plugin.server.pluginManager
    private val skriptPlugin = pm.getPlugin("Skript")
    private lateinit var addon: SkriptAddon

    fun canLoad(): Boolean {
        if (skriptPlugin == null) {
            Util.log("<red>Skript is not installed, disabling plugin!")
            return false
        }
        if (!skriptPlugin.isEnabled) {
            Util.log("<red>Skript is not enabled, disabling plugin!")
            return false
        }
        if (Skript.getVersion().isSmallerThan(Version(2, 7))) {
            Util.log("<red>Skraps requires Skript 2.7 or higher, disabling plugin!")
            return false
        }
        if (!Skript.isAcceptRegistrations()) {
            Util.log("<red>Skript is no longer accepting registrations. Addons can no longer be loaded!")
            val plugman = pm.getPlugin("PlugMan")
            if (plugman != null && plugman.isEnabled) {
                Util.log(
                    "<red>It appears you have PlugMan installed.",
                    "<red>If you are trying to use PlugMan to enable or reload Skraps, you cannot.",
                    "<red>Please restart the server!"
                )
            } else {
                Util.log("<red>Some plugin may be delaying Skraps loading which is after Skript has stopped accepting registrations.")
            }
            return false
        }
        if (!plugin.server.version.contains("Paper")) {
            Util.log(
                "<red>It looks like you are not using Paper.",
                "<red>Skraps is designed to work with Paper and may not work as expected on other server software."
            )
        }

        loadElements()
        return true
    }

    private fun loadElements() {
        addon = Skript.registerAddon(plugin)
        addon.languageFileDirectory = "lang"

        val elementCountBefore = SkriptUtils.getElementCount()

        loadOtherElements()

        loadDataPackElements()
        loadEntityElements()
        loadExperimentalElements()
        loadPluginsElements()

        val elementCountAfter = SkriptUtils.getElementCount()
        val finish = IntArray(elementCountBefore.size)
        var total = 0
        for (i in elementCountBefore.indices) {
            finish[i] = elementCountAfter[i] - elementCountBefore[i]
            total += finish[i]
        }

        val elementNames = arrayOf("event", "effect", "expression", "condition", "section")
        Util.log("Loaded $total elements:")
        for (i in finish.indices) {
            Util.log(" - ${finish[i]} ${elementNames[i]}${if (finish[i] === 1) "" else "s"}")
        }
    }

    private fun loadDataPackElements() {
        if (!config.getBoolean("elements.datapack", true)) {
            Util.logLoading("<blue>Datapack Elements <red>disabled via config.")
            return
        }

        try {
            addon.loadClasses("dev.tarna.skraps.elements.datapack")
            Util.logLoading("<blue>Datapack Elements <green>successfully loaded.")
        } catch (e: IOException) {
            e.printStackTrace()
            Util.log("<red>Failed to load datapack elements, disabling plugin!")
            pm.disablePlugin(plugin)
        }
    }

    private fun loadEntityElements() {
        val entities = listOf("bat", "bee", "camel", "chicken", "dolphin", "dragon", "endermite", "ghast", "goat", "sheep", "slime")
        val enabled = mutableListOf<String>()
        val disabled = mutableListOf<String>()

        for (entity in entities) {
            if (!config.getBoolean("elements.entity.$entity", true)) {
                disabled.add(entity)
                continue
            }

            try {
                addon.loadClasses("dev.tarna.skraps.elements.entity.$entity")
                enabled.add(entity)
            } catch (e: IOException) {
                e.printStackTrace()
                Util.log("<red>Failed to load entity elements, disabling plugin!")
                pm.disablePlugin(plugin)
            }
        }

        if (enabled.isNotEmpty()) Util.logLoading("<blue>Entity Elements for <yellow>${enabled.joinToString(", ")} <green>successfully loaded.")
        if (disabled.isNotEmpty()) Util.logLoading("<blue>Entity Elements for <yellow>${disabled.joinToString(", ")} <red>disabled via config.")
    }

    private fun loadExperimentalElements() {
        if (!config.getBoolean("elements.experimental", true)) {
            Util.logLoading("<blue>Experimental Elements <red>disabled via config.")
            return
        }

        try {
            addon.loadClasses("dev.tarna.skraps.elements.experimental")
            Util.logLoading("<blue>Experimental Elements <green>successfully loaded.")
        } catch (e: IOException) {
            e.printStackTrace()
            Util.log("<red>Failed to load experimental elements, disabling plugin!")
            pm.disablePlugin(plugin)
        }
    }

    private fun loadOtherElements() {
        if (!config.getBoolean("elements.other", true)) {
            Util.logLoading("<blue>Other Elements <red>disabled via config.")
            return
        }

        try {
            addon.loadClasses("dev.tarna.skraps.elements.other")
            Util.logLoading("<blue>Other Elements <green>successfully loaded.")
        } catch (e: IOException) {
            e.printStackTrace()
            Util.log("<red>Failed to load other elements, disabling plugin!")
            pm.disablePlugin(plugin)
        }
    }

    private fun loadPluginsElements() {
        if (!config.getBoolean("elements.plugins", true)) {
            Util.logLoading("<blue>Plugins Elements <red>disabled via config.")
            return
        }

        try {
            addon.loadClasses("dev.tarna.skraps.elements.plugins")
            Util.logLoading("<blue>Plugins Elements <green>successfully loaded.")
        } catch (e: IOException) {
            e.printStackTrace()
            Util.log("<red>Failed to load plugins elements, disabling plugin!")
            pm.disablePlugin(plugin)
        }
    }
}
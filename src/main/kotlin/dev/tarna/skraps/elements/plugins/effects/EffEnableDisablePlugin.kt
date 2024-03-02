package dev.tarna.skraps.elements.plugins.effects

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import org.bukkit.Bukkit
import org.bukkit.event.Event

@Name("Enable/Disable Plugin")
@Description("Enable or disable a plugin.")
@Examples("disable plugin \"Skript\"")
@Since("1.0.0")
class EffEnableDisablePlugin : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffEnableDisablePlugin::class.java,
                "(enable|activate) [the] plugin %strings%",
                "(disable|deactivate) [the] plugin %strings%"
            )
        }
    }

    private lateinit var plugins: Expression<String>

    private var disable = false

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parser: SkriptParser.ParseResult): Boolean {
        plugins = expr[0] as Expression<String>
        disable = matchedPattern == 1
        return true
    }

    override fun execute(event: Event) {
        val plugins = plugins.getArray(event)
        val pm = Bukkit.getPluginManager()

        if (disable) {
            for (plugin in plugins) {
                val pl = pm.getPlugin(plugin)
                if (pl != null) pm.disablePlugin(pl)
            }
        } else {
            for (plugin in plugins) {
                val pl = pm.getPlugin(plugin)
                if (pl != null) pm.enablePlugin(pl)
            }
        }
    }

    override fun toString(event: Event?, b: Boolean): String {
        return "${if (disable) "disable" else "enable"} plugin ${plugins.toString(event, b)}"
    }
}
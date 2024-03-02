package dev.tarna.skraps.elements.plugins.events

import ch.njol.skript.Skript
import ch.njol.skript.lang.util.SimpleEvent
import ch.njol.skript.registrations.EventValues
import ch.njol.skript.util.Getter
import org.bukkit.event.server.PluginDisableEvent
import org.bukkit.event.server.PluginEnableEvent

class PluginEvents : SimpleEvent() {
    companion object {
        init {
            Skript.registerEvent("Plugin Enable", PluginEvents::class.java, PluginEnableEvent::class.java, "plugin (enable|start)")
                .description("Called when a plugin is enabled")
                .examples("on plugin enable:",
                    "\tsend \"Plugin %event-string% has been enabled!\" to console")
                .since("1.0.0")

            EventValues.registerEventValue(
                PluginEnableEvent::class.java,
                String::class.java,
                object : Getter<String, PluginEnableEvent>() {
                    override fun get(event: PluginEnableEvent): String {
                        return event.plugin.name
                    }
                }, 0
            )

            Skript.registerEvent("Plugin Disable", PluginEvents::class.java, PluginDisableEvent::class.java, "plugin (disable|stop)")
                .description("Called when a plugin is disabled")
                .examples("on plugin disable:",
                    "\tsend \"Plugin %event-string% has been disabled!\" to console")
                .since("1.0.0")

            EventValues.registerEventValue(
                PluginDisableEvent::class.java,
                String::class.java,
                object : Getter<String, PluginDisableEvent>() {
                    override fun get(event: PluginDisableEvent): String {
                        return event.plugin.name
                    }
                }, 0
            )
        }
    }
}
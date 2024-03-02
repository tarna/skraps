package dev.tarna.skraps.elements.entity.bat.events

import ch.njol.skript.Skript
import ch.njol.skript.lang.util.SimpleEvent
import ch.njol.skript.registrations.EventValues
import ch.njol.skript.util.Getter
import org.bukkit.event.entity.BatToggleSleepEvent

class BatEvents : SimpleEvent() {
    companion object {
        init {
            Skript.registerEvent("Bat Toggle Sleep", BatEvents::class.java, BatToggleSleepEvent::class.java, "[bat] toggle sleep")
                .description("Called when a bat attempts to sleep or wake up from its slumber.",
                    "If a Bat Toggle Sleep event is cancelled, the Bat will not toggle its sleep state.")
                .examples("on bat toggle sleep:",
                    "\tif event-boolean is true:",
                    "\t\tcancel event")
                .since("1.0.0")

            EventValues.registerEventValue(
                BatToggleSleepEvent::class.java,
                Boolean::class.javaObjectType,
                object : Getter<Boolean, BatToggleSleepEvent>() {
                    override fun get(event: BatToggleSleepEvent): Boolean {
                        return event.isAwake
                    }
                }, 0
            )
        }
    }
}
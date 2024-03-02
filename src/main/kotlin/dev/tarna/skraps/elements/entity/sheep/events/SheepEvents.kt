package dev.tarna.skraps.elements.entity.sheep.events

import ch.njol.skript.Skript
import ch.njol.skript.lang.util.SimpleEvent
import ch.njol.skript.registrations.EventValues
import ch.njol.skript.util.Getter
import ch.njol.skript.util.SkriptColor
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.entity.SheepDyeWoolEvent

class SheepEvents : SimpleEvent() {
    companion object {
        init {
            Skript.registerEvent("Sheep Dye Wool", SheepEvents::class.java, SheepDyeWoolEvent::class.java, "sheep dye wool")
                .description("Fired when a sheep's wool is dyed.")
                .examples("on sheep dye wool:",
                    "\tsend \"Sheep %event-entity% has had its wool dyed!\" to console")
                .since("1.0.0")

            EventValues.registerEventValue(
                SheepDyeWoolEvent::class.java,
                LivingEntity::class.java,
                object : Getter<LivingEntity, SheepDyeWoolEvent>() {
                    override fun get(event: SheepDyeWoolEvent): LivingEntity {
                        return event.entity
                    }
                }, 0
            )

            EventValues.registerEventValue(
                SheepDyeWoolEvent::class.java,
                Player::class.java,
                object : Getter<Player, SheepDyeWoolEvent>() {
                    override fun get(event: SheepDyeWoolEvent): Player? {
                        return event.player
                    }
                }, 0
            )

            EventValues.registerEventValue(
                SheepDyeWoolEvent::class.java,
                SkriptColor::class.java,
                object : Getter<SkriptColor, SheepDyeWoolEvent>() {
                    override fun get(event: SheepDyeWoolEvent): SkriptColor {
                        return SkriptColor.fromDyeColor(event.color)
                    }
                }, 0
            )
        }
    }
}
package dev.tarna.skraps.elements.entity.slime.events

import ch.njol.skript.Skript
import ch.njol.skript.lang.util.SimpleEvent
import ch.njol.skript.registrations.EventValues
import ch.njol.skript.util.Getter
import com.destroystokyo.paper.event.entity.SlimeChangeDirectionEvent
import com.destroystokyo.paper.event.entity.SlimeSwimEvent
import com.destroystokyo.paper.event.entity.SlimeWanderEvent
import org.bukkit.entity.LivingEntity

class SlimeEvents : SimpleEvent() {
    companion object {
        init {
            if (Skript.classExists("com.destroystokyo.paper.event.entity.SlimeChangeDirectionEvent")) {
                Skript.registerEvent("Slime Change Direction", SlimeEvents::class.java, SlimeChangeDirectionEvent::class.java, "slime change direction")
                    .description("Fired when a Slime decides to change its facing direction.",
                        "This event does not fire for the entity's actual movement. Only when it is choosing to change direction.")
                    .examples("on slime change direction:",
                        "\tsend \"Slime %event-entity% has changed direction!\" to console")
                    .since("1.0.0")

                EventValues.registerEventValue(
                    SlimeChangeDirectionEvent::class.java,
                    LivingEntity::class.java,
                    object : Getter<LivingEntity, SlimeChangeDirectionEvent>() {
                        override fun get(event: SlimeChangeDirectionEvent): LivingEntity {
                            return event.entity
                        }
                    }, 0
                )
            }

            if (Skript.classExists("com.destroystokyo.paper.event.entity.SlimeSwimEvent")) {
                Skript.registerEvent("Slime Swim", SlimeEvents::class.java, SlimeSwimEvent::class.java, "slime swim")
                    .description("Fired when a Slime decides to start jumping while swimming in water/lava.",
                        "This event does not fire for the entity's actual movement. Only when it is choosing to start jumping.")
                    .examples("on slime swim:",
                        "\tsend \"Slime %event-entity% has started swimming!\" to console")
                    .since("1.0.0")

                EventValues.registerEventValue(
                    SlimeSwimEvent::class.java,
                    LivingEntity::class.java,
                    object : Getter<LivingEntity, SlimeSwimEvent>() {
                        override fun get(event: SlimeSwimEvent): LivingEntity {
                            return event.entity
                        }
                    }, 0
                )
            }

            if (Skript.classExists("com.destroystokyo.paper.event.entity.SlimeWanderEvent")) {
                Skript.registerEvent("Slime Wander", SlimeEvents::class.java, SlimeWanderEvent::class.java, "slime wander")
                    .description("Fired when a Slime decides to start wandering.",
                        "This event does not fire for the entity's actual movement. Only when it is choosing to start moving.")
                    .examples("on slime wander:",
                        "\tsend \"Slime %event-entity% has wandered!\" to console")
                    .since("1.0.0")

                EventValues.registerEventValue(
                    SlimeWanderEvent::class.java,
                    LivingEntity::class.java,
                    object : Getter<LivingEntity, SlimeWanderEvent>() {
                        override fun get(event: SlimeWanderEvent): LivingEntity {
                            return event.entity
                        }
                    }, 0
                )
            }
        }
    }
}
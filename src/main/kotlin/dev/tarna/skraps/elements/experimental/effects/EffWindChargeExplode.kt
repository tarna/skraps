package dev.tarna.skraps.elements.experimental.effects

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import org.bukkit.entity.Entity
import org.bukkit.entity.WindCharge
import org.bukkit.event.Event

@Name("Explode Wind Charge")
@Description("Immediately explodes the wind charge.",
    "The wind charge is an experimental entity."
)
@Examples("explode wind charge {_windCharge}")
@Since("1.0.0")
class EffWindChargeExplode : Effect() {
    companion object {
        init {
            if (Skript.classExists("org.bukkit.entity.WindCharge"))
                Skript.registerEffect(EffWindChargeExplode::class.java, "explode wind charge %entities%")
        }
    }

    private lateinit var entities: Expression<Entity>

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: SkriptParser.ParseResult): Boolean {
        entities = expr[0] as Expression<Entity>
        return true
    }

    override fun execute(event: Event) {
        val entities = entities.getArray(event)
            .filterIsInstance<WindCharge>()

        entities.forEach { it.explode() }
    }

    override fun toString(event: Event?, b: Boolean): String {
        return "explode wind charge ${entities.toString(event, b)}"
    }
}
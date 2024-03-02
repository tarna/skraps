package dev.tarna.skraps.elements.entity.bat.expressions

import ch.njol.skript.classes.Changer.ChangeMode
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.expressions.base.PropertyExpression
import ch.njol.skript.expressions.base.SimplePropertyExpression
import org.bukkit.Location
import org.bukkit.entity.Bat
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event

@Name("Bat Target Location")
@Description("The current target location of a bat.",
    "This can be set to null to cause the bat to recalculate its target location"
)
@Examples("on spawn of bat:",
    "\tset {_} to event-entity",
    "\twhile {_} is alive:",
    "\t\tset target location of {_} to player(\"_Tarna_\")",
    "\t\twait 1 second"
)
@Since("1.0.0")
class ExprBatTargetLocation : SimplePropertyExpression<LivingEntity, Location>() {
    companion object {
        init {
            PropertyExpression.register(ExprBatTargetLocation::class.java, Location::class.java, "target location", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): Location? {
        return if (entity is Bat) entity.targetLocation else null
    }

    override fun acceptChange(mode: ChangeMode): Array<Class<*>?>? {
        if (mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.DELETE) return arrayOf(Location::class.java)
        return null
    }

    override fun change(event: Event?, delta: Array<out Any?>?, mode: ChangeMode) {
        val change = if (delta != null) delta[0] as Location else return
        val entity = expr.getSingle(event)
        if (entity !is Bat) return

        when (mode) {
            ChangeMode.SET -> entity.targetLocation = change
            ChangeMode.RESET, ChangeMode.DELETE -> entity.targetLocation = null
            else -> {}
        }
    }

    override fun getReturnType(): Class<out Location> {
        return Location::class.java
    }

    override fun getPropertyName(): String {
        return "target location"
    }
}
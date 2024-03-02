package dev.tarna.skraps.elements.entity.dolphin.expressions

import ch.njol.skript.classes.Changer.ChangeMode
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.expressions.base.SimplePropertyExpression
import org.bukkit.Location
import org.bukkit.entity.Dolphin
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event

@Name("Dolphin Treasure Location")
@Description("The treasure location this dolphin tries to guide players to.")
@Examples("set treasure location of {_dolphin} to {guide::%player's uuid%}")
@Since("1.0.0")
class ExprDolphinTreasureLocation : SimplePropertyExpression<LivingEntity, Location>() {
    companion object {
        init {
            register(ExprDolphinTreasureLocation::class.java, Location::class.java, "treasure location", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): Location? {
        return if (entity is Dolphin) entity.treasureLocation else null
    }

    override fun getReturnType(): Class<out Location> {
        return Location::class.java
    }

    override fun acceptChange(mode: ChangeMode): Array<Class<*>?>? {
        if (mode == ChangeMode.SET || mode == ChangeMode.RESET) return arrayOf(Location::class.java)
        return null
    }

    override fun change(event: Event?, delta: Array<out Any?>?, mode: ChangeMode) {
        val change = if (delta != null) delta[0] as Location else return
        val entity = expr.getSingle(event) ?: return
        if (entity !is Dolphin) return

        when (mode) {
            ChangeMode.SET -> entity.treasureLocation = change
            else -> {}
        }
    }

    override fun getPropertyName(): String {
        return "dolphin treasure location"
    }
}
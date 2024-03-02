package dev.tarna.skraps.elements.entity.bee.expressions

import ch.njol.skript.classes.Changer.ChangeMode
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.expressions.base.SimplePropertyExpression
import org.bukkit.Location
import org.bukkit.entity.Bee
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event

@Name("Bee Flower")
@Description("The bee's flower location.")
@Examples("set flower of {_bee} to location(100, 256, -100)")
@Since("1.0.0")
class ExprBeeFlower : SimplePropertyExpression<LivingEntity, Location>() {
    companion object {
        init {
            register(ExprBeeFlower::class.java, Location::class.java, "flower [location]", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): Location? {
        return if (entity is Bee) entity.flower else null
    }

    override fun getReturnType(): Class<out Location> {
        return Location::class.java
    }

    override fun acceptChange(mode: ChangeMode): Array<Class<*>?>? {
        if (mode == ChangeMode.SET || mode == ChangeMode.RESET) return arrayOf(Location::class.java)
        return null
    }

    override fun change(event: Event?, delta: Array<out Any?>?, mode: ChangeMode) {
        val change = if (delta != null) delta[0] as Location? else return
        val entity = expr.getSingle(event) ?: return
        if (entity !is Bee) return

        when (mode) {
            ChangeMode.SET -> entity.flower = change
            ChangeMode.RESET -> entity.flower = null
            else -> {}
        }
    }

    override fun getPropertyName(): String {
        return "bee flower location"
    }
}
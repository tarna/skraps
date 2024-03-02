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

@Name("Bee Hive")
@Description("The bee's hive location.")
@Examples("set hive of {_bee} to location(100, 256, -100)")
@Since("1.0.0")
class ExprBeeHive : SimplePropertyExpression<LivingEntity, Location>() {
    companion object {
        init {
            register(ExprBeeHive::class.java, Location::class.java, "hive [location]", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): Location? {
        return if (entity is Bee) entity.hive else null
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
            ChangeMode.SET -> entity.hive = change
            ChangeMode.RESET -> entity.hive = null
            else -> {}
        }
    }

    override fun getPropertyName(): String {
        return "bee hive location"
    }
}
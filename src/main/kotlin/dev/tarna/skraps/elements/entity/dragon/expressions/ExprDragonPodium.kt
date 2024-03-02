package dev.tarna.skraps.elements.entity.dragon.expressions

import ch.njol.skript.classes.Changer.ChangeMode
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.expressions.base.SimplePropertyExpression
import org.bukkit.Location
import org.bukkit.entity.EnderDragon
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event

@Name("Dragon Podium")
@Description("Podium location used by the dragon.")
@Examples("set the podium location of {_dragon} to location(100, 256, -100)")
@Since("1.0.0")
class ExprDragonPodium : SimplePropertyExpression<LivingEntity, Location>() {
    companion object {
        init {
            register(ExprDragonPodium::class.java, Location::class.java, "[the] [dragon] podium [location]", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): Location? {
        return if (entity is EnderDragon) entity.podium else null
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
        if (entity !is EnderDragon) return

        when (mode) {
            ChangeMode.SET -> entity.setPodium(change)
            ChangeMode.RESET -> entity.setPodium(Location(entity.world, 0.0, 65.0, 0.0))
            else -> {}
        }
    }

    override fun getPropertyName(): String {
        return "dragon podium location"
    }
}
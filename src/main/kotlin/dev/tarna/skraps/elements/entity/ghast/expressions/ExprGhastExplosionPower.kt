package dev.tarna.skraps.elements.entity.ghast.expressions

import ch.njol.skript.classes.Changer.ChangeMode
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.expressions.base.SimplePropertyExpression
import org.bukkit.entity.Ghast
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event

@Name("Ghast Explosion Power")
@Description("The explosion power of shot fireballs.")
@Examples("set explosion power of {_ghast} to 3")
@Since("1.0.0")
class ExprGhastExplosionPower : SimplePropertyExpression<LivingEntity, Number>() {
    companion object {
        init {
            register(ExprGhastExplosionPower::class.java, Number::class.java, "explosion power", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): Number? {
        return if (entity is Ghast) entity.explosionPower else null
    }

    override fun getReturnType(): Class<out Number> {
        return Number::class.java
    }

    override fun acceptChange(mode: ChangeMode): Array<Class<*>?>? {
        if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE) return arrayOf(Number::class.java)
        return null
    }

    override fun change(event: Event?, delta: Array<out Any?>?, mode: ChangeMode) {
        val change = if (delta != null) delta[0] as Number else return
        val entity = expr.getSingle(event)
        if (entity !is Ghast) return

        when (mode) {
            ChangeMode.SET -> entity.explosionPower = change.toInt()
            ChangeMode.ADD -> entity.explosionPower += change.toInt()
            ChangeMode.REMOVE -> entity.explosionPower -= change.toInt()
            else -> {}
        }
    }

    override fun getPropertyName(): String {
        return "explosion power"
    }
}
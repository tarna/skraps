package dev.tarna.skraps.elements.entity.endermite.expressions

import ch.njol.skript.classes.Changer.ChangeMode
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.expressions.base.SimplePropertyExpression
import org.bukkit.entity.Endermite
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event

@Name("Endermite Lifetime Ticks")
@Description("How long this endermite has been living for.")
@Examples("set {_ticks} to the lifetime ticks of the {_endermite}")
@Since("1.0.0")
class ExprEndermiteLifetimeTicks : SimplePropertyExpression<LivingEntity, Number>() {
    companion object {
        init {
            register(ExprEndermiteLifetimeTicks::class.java, Number::class.java, "[the] life[ ]time ticks", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): Number? {
        return if (entity is Endermite) entity.lifetimeTicks else null
    }

    override fun getReturnType(): Class<out Number> {
        return Number::class.java
    }

    override fun acceptChange(mode: ChangeMode): Array<Class<*>?>? {
        if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.RESET) return arrayOf(Number::class.java)
        return null
    }

    override fun change(event: Event?, delta: Array<out Any?>?, mode: ChangeMode) {
        val change = if (delta != null) delta[0] as Number else return
        val entity = expr.getSingle(event) ?: return
        if (entity !is Endermite) return

        when (mode) {
            ChangeMode.SET -> entity.lifetimeTicks = change.toInt()
            ChangeMode.ADD -> entity.lifetimeTicks += change.toInt()
            ChangeMode.REMOVE -> entity.lifetimeTicks -= change.toInt()
            ChangeMode.RESET -> entity.lifetimeTicks = 0
            else -> {}
        }
    }

    override fun getPropertyName(): String {
        return "endermite lifetime ticks"
    }
}
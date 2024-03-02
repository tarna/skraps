package dev.tarna.skraps.elements.entity.slime.expressions

import ch.njol.skript.classes.Changer.ChangeMode
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.expressions.base.SimplePropertyExpression
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Slime
import org.bukkit.event.Event

@Name("Slime Size")
@Description("Setting the size of the slime (regardless of previous size) will set the following attributes:",
    "- Attribute.GENERIC_MAX_HEALTH",
    "- Attribute.GENERIC_MOVEMENT_SPEED",
    "- Attribute.GENERIC_ATTACK_DAMAGE",
    "to their per-size defaults and heal the slime to its max health (assuming it's alive)."
)
@Examples("set size of {_slime} to 3")
@Since("1.0.0")
class ExprSlimeSize : SimplePropertyExpression<LivingEntity, Number>() {
    companion object {
        init {
            register(ExprSlimeSize::class.java, Number::class.java, "size", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): Number? {
        return if (entity is Slime) entity.size else null
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
        if (entity !is Slime) return

        when (mode) {
            ChangeMode.SET -> entity.size = change.toInt()
            ChangeMode.ADD -> entity.size += change.toInt()
            ChangeMode.REMOVE -> entity.size -= change.toInt()
            else -> {}
        }
    }

    override fun getPropertyName(): String {
        return "slime size"
    }
}
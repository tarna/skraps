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

@Name("Slime Can Wander")
@Description("Whether this slime can randomly wander/jump around on its own")
@Examples("set can wander of {_slime} to true")
@Since("1.0.0")
class ExprSlimeWander : SimplePropertyExpression<LivingEntity, Boolean>() {
    companion object {
        init {
            register(ExprSlimeWander::class.java, Boolean::class.javaObjectType, "can wander", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): Boolean? {
        return if (entity is Slime) entity.canWander() else null
    }

    override fun getReturnType(): Class<out Boolean> {
        return Boolean::class.javaObjectType
    }

    override fun acceptChange(mode: ChangeMode): Array<Class<*>?>? {
        if (mode == ChangeMode.SET) return arrayOf(Boolean::class.javaObjectType)
        return null
    }

    override fun change(event: Event?, delta: Array<out Any?>?, mode: ChangeMode) {
        val change = if (delta != null) delta[0] as Boolean else return
        val entity = expr.getSingle(event) ?: return
        if (entity !is Slime) return

        when (mode) {
            ChangeMode.SET -> entity.setWander(change)
            else -> {}
        }
    }

    override fun getPropertyName(): String {
        return "slime can wander"
    }
}
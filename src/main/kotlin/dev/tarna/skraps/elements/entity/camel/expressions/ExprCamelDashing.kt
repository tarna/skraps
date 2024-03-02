package dev.tarna.skraps.elements.entity.camel.expressions

import ch.njol.skript.classes.Changer.ChangeMode
import ch.njol.skript.expressions.base.SimplePropertyExpression
import org.bukkit.entity.Camel
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event

class ExprCamelDashing : SimplePropertyExpression<LivingEntity, Boolean>() {
    companion object {
        init {
            register(ExprCamelDashing::class.java, Boolean::class.javaObjectType, "dashing", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): Boolean? {
        return if (entity is Camel) entity.isDashing else null
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
        if (entity !is Camel) return

        when (mode) {
            ChangeMode.SET -> entity.isDashing = change
            else -> {}
        }
    }

    override fun getPropertyName(): String {
        return "camel dashing"
    }
}
package dev.tarna.skraps.elements.entity.dolphin.expressions

import ch.njol.skript.classes.Changer.ChangeMode
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.expressions.base.SimplePropertyExpression
import org.bukkit.entity.Dolphin
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event

@Name("Dolphin Has Fish")
@Description("If this dolphin has a fish.")
@Examples("set has fish of {_dolphin} to true")
@Since("1.0.0")
class ExprDolphinHasFish : SimplePropertyExpression<LivingEntity, Boolean>() {
    companion object {
        init {
            register(ExprDolphinHasFish::class.java, Boolean::class.javaObjectType, "has fish", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): Boolean? {
        return if (entity is Dolphin) entity.hasFish() else null
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
        if (entity !is Dolphin) return

        when (mode) {
            ChangeMode.SET -> entity.setHasFish(change)
            else -> {}
        }
    }

    override fun getPropertyName(): String {
        return "dolphin has fish"
    }
}
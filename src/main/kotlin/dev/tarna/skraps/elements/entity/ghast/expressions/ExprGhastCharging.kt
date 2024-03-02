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

@Name("Ghast Charging")
@Description("Whether the Ghast is charging.")
@Examples("set charging of {_ghast} to true")
@Since("1.0.0")
class ExprGhastCharging : SimplePropertyExpression<LivingEntity, Boolean>() {
    companion object {
        init {
            register(ExprGhastCharging::class.java, Boolean::class.javaObjectType, "charging", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): Boolean? {
        return if (entity is Ghast) entity.isCharging else null
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
        if (entity !is Ghast) return

        when (mode) {
            ChangeMode.SET -> entity.isCharging = change
            else -> {}
        }
    }

    override fun getPropertyName(): String {
        return "charging"
    }
}
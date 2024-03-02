package dev.tarna.skraps.elements.entity.goat.expressions

import ch.njol.skript.classes.Changer.ChangeMode
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.expressions.base.SimplePropertyExpression
import org.bukkit.entity.Goat
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event

@Name("Goat Screaming")
@Description("Whether the goat is screaming.")
@Examples("set screaming of {_goat} to true")
@Since("1.0.0")
class ExprGoatScreaming : SimplePropertyExpression<LivingEntity, Boolean>() {
    companion object {
        init {
            register(ExprGoatScreaming::class.java, Boolean::class.javaObjectType, "[is] screaming", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): Boolean? {
        return if (entity is Goat) entity.isScreaming else null
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
        if (entity !is Goat) return

        when (mode) {
            ChangeMode.SET -> entity.isScreaming = change
            else -> {}
        }
    }

    override fun getPropertyName(): String {
        return "goat screaming"
    }
}
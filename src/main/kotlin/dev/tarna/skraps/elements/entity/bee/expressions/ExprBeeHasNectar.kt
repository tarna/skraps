package dev.tarna.skraps.elements.entity.bee.expressions

import ch.njol.skript.classes.Changer.ChangeMode
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.expressions.base.SimplePropertyExpression
import org.bukkit.entity.Bee
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event

@Name("Bee Has Nectar")
@Description("Whether or not the bee has nectar.")
@Examples("set has nectar of {_bee} to true")
@Since("1.0.0")
class ExprBeeHasNectar : SimplePropertyExpression<LivingEntity, Boolean>() {
    companion object {
        init {
            register(ExprBeeHasNectar::class.java, Boolean::class.javaObjectType, "has nectar", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): Boolean? {
        return if (entity is Bee) entity.hasNectar() else null
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
        if (entity !is Bee) return

        when (mode) {
            ChangeMode.SET -> entity.setHasNectar(change)
            else -> {}
        }
    }

    override fun getPropertyName(): String {
        return "bee has nector"
    }
}
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

@Name("Dolphin Moistness")
@Description("The moistness level of a dolphin",
    "Once this is less than 0 the dolphin will start to take damage."
)
@Examples("set moistness of {_dolphin} to 100")
@Since("1.0.0")
class ExprDolphinMoistness : SimplePropertyExpression<LivingEntity, Number>() {
    companion object {
        init {
            register(ExprDolphinMoistness::class.java, Number::class.java, "moistness", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): Number? {
        return if (entity is Dolphin) entity.moistness else null
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
        val entity = expr.getSingle(event)
        if (entity !is Dolphin) return

        when (mode) {
            ChangeMode.SET -> entity.moistness = change.toInt()
            ChangeMode.ADD -> entity.moistness += change.toInt()
            ChangeMode.REMOVE -> entity.moistness -= change.toInt()
            ChangeMode.RESET -> entity.moistness = 0
            else -> {}
        }
    }

    override fun getPropertyName(): String {
        return "dolphin moistness"
    }
}
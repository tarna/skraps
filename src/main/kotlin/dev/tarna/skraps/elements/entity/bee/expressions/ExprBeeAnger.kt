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

@Name("Bee Anger")
@Description("The current anger level of a bee.",
    "This is the amount of ticks the bee will be angry for."
)
@Examples("set anger level of {_bee} to 20 * 60")
@Since("1.0.0")
class ExprBeeAnger : SimplePropertyExpression<LivingEntity, Number>() {
    companion object {
        init {
            register(ExprBeeAnger::class.java, Number::class.java, "anger level", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): Number? {
        return if (entity is Bee) entity.anger else null
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
        if (entity !is Bee) return

        when (mode) {
            ChangeMode.SET -> entity.anger = change.toInt()
            ChangeMode.ADD -> entity.anger += change.toInt()
            ChangeMode.REMOVE -> entity.anger -= change.toInt()
            ChangeMode.RESET -> entity.anger = 0
            else -> {}
        }
    }

    override fun getPropertyName(): String {
        return "anger"
    }
}
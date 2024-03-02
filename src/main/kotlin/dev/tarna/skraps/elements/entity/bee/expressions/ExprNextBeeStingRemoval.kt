package dev.tarna.skraps.elements.entity.bee.expressions

import ch.njol.skript.classes.Changer.ChangeMode
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.expressions.base.SimplePropertyExpression
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event

@Name("Next Bee Sting Removal")
@Description("The time in ticks until the next stinger leaves the entity's body.",
    "A value of 0 will cause the server to re-calculate the amount of ticks on the next tick."
)
@Examples("set the next bee stinger removal of player to 1000")
@Since("1.0.0")
class ExprNextBeeStingRemoval : SimplePropertyExpression<LivingEntity, Number>() {
    companion object {
        init {
            register(ExprBeeStingersInBody::class.java, Number::class.java, "[the] next [bee] stinger removal", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): Number {
        return entity.beeStingersInBody
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

        when (mode) {
            ChangeMode.SET -> entity.nextBeeStingerRemoval = change.toInt()
            ChangeMode.ADD -> entity.nextBeeStingerRemoval += change.toInt()
            ChangeMode.REMOVE -> entity.nextBeeStingerRemoval -= change.toInt()
            ChangeMode.RESET -> entity.nextBeeStingerRemoval = 0
            else -> {}
        }
    }

    override fun getPropertyName(): String {
        return "next bee sting removal"
    }
}
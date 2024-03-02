package dev.tarna.skraps.elements.entity.bee.expressions

import ch.njol.skript.classes.Changer.ChangeMode
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.expressions.base.SimplePropertyExpression
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event

@Name("Bee Stingers In Body")
@Description("The amount of stingers in the entity's body.")
@Examples("set the bee stingers in body of player to 0")
@Since("1.0.0")
class ExprBeeStingersInBody : SimplePropertyExpression<LivingEntity, Number>() {
    companion object {
        init {
            register(ExprBeeStingersInBody::class.java, Number::class.java, "[the] [amount of] [bee] stingers in body", "livingentities")
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
            ChangeMode.SET -> entity.beeStingersInBody = change.toInt()
            ChangeMode.ADD -> entity.beeStingersInBody += change.toInt()
            ChangeMode.REMOVE -> entity.beeStingersInBody -= change.toInt()
            ChangeMode.RESET -> entity.beeStingersInBody = 0
            else -> {}
        }
    }

    override fun getPropertyName(): String {
        return "stinger in body"
    }
}
package dev.tarna.skraps.elements.entity.bee.expressions

import ch.njol.skript.classes.Changer.ChangeMode
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.expressions.base.SimplePropertyExpression
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event

@Name("Bee Stinger Cooldown")
@Description("The time in ticks until the next stinger leaves the entity's body.")
@Examples("set the bee stinger cooldown of player to 1000")
@Since("1.0.0")
class ExprBeeStingerCooldown : SimplePropertyExpression<LivingEntity, Number>() {
    companion object {
        init {
            register(ExprBeeStingerCooldown::class.java, Number::class.java, "[the] [bee] stinger cool[ ]down", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): Number {
        return entity.beeStingerCooldown
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
            ChangeMode.SET -> entity.beeStingerCooldown = change.toInt()
            ChangeMode.ADD -> entity.beeStingerCooldown += change.toInt()
            ChangeMode.REMOVE -> entity.beeStingerCooldown -= change.toInt()
            ChangeMode.RESET -> entity.beeStingerCooldown = 0
            else -> {}
        }
    }

    override fun getPropertyName(): String {
        return "stinger cooldown"
    }
}
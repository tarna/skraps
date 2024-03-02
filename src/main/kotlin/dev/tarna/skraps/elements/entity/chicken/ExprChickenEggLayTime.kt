package dev.tarna.skraps.elements.entity.chicken

import ch.njol.skript.classes.Changer.ChangeMode
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.expressions.base.SimplePropertyExpression
import ch.njol.skript.util.Timespan
import org.bukkit.entity.Chicken
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event

@Name("Chicken Egg Lay Time")
@Description("The number of ticks till this chicken lays an egg.")
@Examples("set the egg lay time of {_chicken} to 10 seconds")
@Since("1.0.0")
class ExprChickenEggLayTime : SimplePropertyExpression<LivingEntity, Timespan>() {
    companion object {
        init {
            register(ExprChickenEggLayTime::class.java, Timespan::class.java, "[the] egg lay[ing] time", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): Timespan? {
        return if (entity is Chicken) Timespan.fromTicks(entity.eggLayTime.toLong()) else null
    }

    override fun getReturnType(): Class<out Timespan> {
        return Timespan::class.java
    }

    override fun acceptChange(mode: ChangeMode): Array<Class<*>?>? {
        if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.RESET) return arrayOf(Timespan::class.java)
        return null
    }

    override fun change(event: Event?, delta: Array<out Any?>?, mode: ChangeMode) {
        val change = if (delta != null) delta[0] as Timespan else return
        val entity = expr.getSingle(event) ?: return
        if (entity !is Chicken) return

        when (mode) {
            ChangeMode.SET -> entity.eggLayTime = change.ticks.toInt()
            ChangeMode.ADD -> entity.eggLayTime += change.ticks.toInt()
            ChangeMode.REMOVE -> entity.eggLayTime -= change.ticks.toInt()
            ChangeMode.RESET -> entity.eggLayTime = 0
            else -> {}
        }
    }

    override fun getPropertyName(): String {
        return "chicken egg lay time"
    }
}
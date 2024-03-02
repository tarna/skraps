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

@Name("Bee Cannot Enter Hive Ticks")
@Description("The amount of ticks the bee cannot enter a hive for.")
@Examples("set cannot enter hive ticks of {_bee} to 20 * 60")
@Since("1.0.0")
class ExprBeeCannotEnterHiveTicks : SimplePropertyExpression<LivingEntity, Number>() {
    companion object {
        init {
            register(ExprBeeCannotEnterHiveTicks::class.java, Number::class.java, "[the] can[ ]not enter hive ticks", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): Number? {
        return if (entity is Bee) entity.cannotEnterHiveTicks else null
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
        if (entity !is Bee) return

        when (mode) {
            ChangeMode.SET -> entity.cannotEnterHiveTicks = change.toInt()
            ChangeMode.ADD -> entity.cannotEnterHiveTicks += change.toInt()
            ChangeMode.REMOVE -> entity.cannotEnterHiveTicks -= change.toInt()
            ChangeMode.RESET -> entity.cannotEnterHiveTicks = 0
            else -> {}
        }
    }

    override fun getPropertyName(): String {
        return "bee cannot enter hive ticks"
    }
}
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

@Name("Bee Crops Grown Since Pollination")
@Description("How many crops this bee has grown since it last pollinated.")
@Examples("broadcast the crops grown since pollination of {_bee}")
@Since("1.0.0")
class ExprBeeCropsGrownSincePollination : SimplePropertyExpression<LivingEntity, Number>() {
    companion object {
        init {
            register(ExprBeeCropsGrownSincePollination::class.java, Number::class.java, "[the] crops grown since [last] pollination", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): Number? {
        return if (entity is Bee) entity.cropsGrownSincePollination else null
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
            ChangeMode.SET -> entity.cropsGrownSincePollination = change.toInt()
            ChangeMode.ADD -> entity.cropsGrownSincePollination += change.toInt()
            ChangeMode.REMOVE -> entity.cropsGrownSincePollination -= change.toInt()
            ChangeMode.RESET -> entity.cropsGrownSincePollination = 0
            else -> {}
        }
    }

    override fun getPropertyName(): String {
        return "crops grown since pollination"
    }
}
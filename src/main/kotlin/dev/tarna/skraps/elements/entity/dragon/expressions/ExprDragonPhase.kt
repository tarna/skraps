package dev.tarna.skraps.elements.entity.dragon.expressions

import ch.njol.skript.classes.Changer.ChangeMode
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.expressions.base.SimplePropertyExpression
import org.bukkit.entity.EnderDragon
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event

@Name("Dragon Phase")
@Description("The current phase of an ender dragon.")
@Examples("set dragon phase of {_dragon} to land on portal")
@Since("1.0.0")
class ExprDragonPhase : SimplePropertyExpression<LivingEntity, EnderDragon.Phase>() {
    companion object {
        init {
            register(ExprDragonPhase::class.java, EnderDragon.Phase::class.java, "dragon phase", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): EnderDragon.Phase? {
        return if (entity is EnderDragon) entity.phase else null
    }

    override fun acceptChange(mode: ChangeMode): Array<Class<*>?>? {
        if (mode == ChangeMode.SET) return arrayOf(EnderDragon.Phase::class.java)
        return null
    }

    override fun change(event: Event?, delta: Array<out Any?>?, mode: ChangeMode) {
        val change = if (delta != null) delta[0] as EnderDragon.Phase else return
        val entity = expr.getSingle(event) ?: return
        if (entity !is EnderDragon) return

        when (mode) {
            ChangeMode.SET -> entity.phase = change
            else -> {}
        }
    }

    override fun getReturnType(): Class<out EnderDragon.Phase> {
        return EnderDragon.Phase::class.java
    }

    override fun getPropertyName(): String {
        return "dragon phase"
    }
}
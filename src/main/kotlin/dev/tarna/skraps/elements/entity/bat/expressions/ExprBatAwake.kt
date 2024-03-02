package dev.tarna.skraps.elements.entity.bat.expressions

import ch.njol.skript.classes.Changer.ChangeMode
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.expressions.base.SimplePropertyExpression
import org.bukkit.entity.Bat
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event

@Name("Bat Awake")
@Description("The current wake state of a bat.",
    "This does not prevent a bat from spontaneously awaking itself, or from reattaching itself to a block."
)
@Examples("on spawn of bat:",
    "\tset {_} to event-entity",
    "\twhile {_} is alive:",
    "\t\tset awake state of {_} to false",
    "\t\twait 1 tick"
)
@Since("1.0.0")
class ExprBatAwake : SimplePropertyExpression<LivingEntity, Boolean>() {
    companion object {
        init {
            register(ExprBatAwake::class.java, Boolean::class.javaObjectType, "awake (state|mode)", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): Boolean {
        return entity is Bat && entity.isAwake
    }

    override fun acceptChange(mode: ChangeMode): Array<Class<*>?>? {
        if (mode == ChangeMode.SET || mode == ChangeMode.RESET) return arrayOf(Boolean::class.javaObjectType)
        return null
    }

    override fun change(event: Event?, delta: Array<out Any?>?, mode: ChangeMode) {
        val change = if (delta != null) delta[0] as Boolean else return
        val entity = expr.getSingle(event)
        if (entity !is Bat) return

        when (mode) {
            ChangeMode.SET -> entity.isAwake = change
            ChangeMode.RESET -> entity.isAwake = false
            else -> {}
        }
    }

    override fun getReturnType(): Class<out Boolean> {
        return Boolean::class.javaObjectType
    }

    override fun getPropertyName(): String {
        return "awake"
    }
}
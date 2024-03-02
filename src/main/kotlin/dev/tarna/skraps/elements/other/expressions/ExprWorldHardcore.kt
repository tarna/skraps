package dev.tarna.skraps.elements.other.expressions

import ch.njol.skript.classes.Changer.ChangeMode
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.expressions.base.PropertyExpression
import ch.njol.skript.expressions.base.SimplePropertyExpression
import ch.njol.util.coll.CollectionUtils
import org.bukkit.World
import org.bukkit.event.Event

@Name("World Hardcore")
@Description("Whether the world is hardcore or not. In a hardcore world the difficulty is locked to hard.")
@Examples("on load:",
    "\tif hardcore mode of world(\"world\" is false:",
    "\t\tset hardcore mode of world(\"world\") to true",
    "\t\tbroadcast \"&cHardcore mode has been enabled for the world!\""
)
@Since("1.0.0")
class ExprWorldHardcore : SimplePropertyExpression<World, Boolean>() {
    companion object {
        init {
            PropertyExpression.register(ExprWorldHardcore::class.java, Boolean::class.javaObjectType, "hardcore (state|mode)", "world")
        }
    }

    override fun convert(world: World): Boolean {
        return world.isHardcore
    }

    override fun acceptChange(mode: ChangeMode): Array<Class<*>?>? {
        if (mode == ChangeMode.RESET || mode == ChangeMode.DELETE || mode == ChangeMode.REMOVE) return null
        return CollectionUtils.array(Boolean::class.javaObjectType)
    }

    override fun change(event: Event, delta: Array<out Any?>?, mode: ChangeMode) {
        val change = if (delta != null) delta[0] as Boolean else return
        val world = expr.getSingle(event) ?: return

        when (mode) {
            ChangeMode.SET -> world.isHardcore = change
            ChangeMode.RESET, ChangeMode.REMOVE, ChangeMode.DELETE -> world.isHardcore = false
            else -> {}
        }
    }

    override fun getReturnType(): Class<Boolean> {
        return Boolean::class.javaObjectType
    }

    override fun getPropertyName(): String {
        return "hardcore"
    }
}
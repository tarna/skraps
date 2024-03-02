package dev.tarna.skraps.elements.other.expressions

import ch.njol.skript.Skript
import ch.njol.skript.classes.Changer.ChangeMode
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import ch.njol.util.coll.CollectionUtils
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.event.Event

@Name("Default Gamemode")
@Description("The default game mode of the server.")
@Examples("on load:",
    "\tif default game mode of the server is not survival:",
    "\t\tset default game mode of the server to survival",
    "\t\tbroadcast \"&cThe default game mode has been set to survival!\""
)
@Since("1.0.0")
class ExprDefaultGamemode : SimpleExpression<GameMode>() {
    companion object {
        init {
            Skript.registerExpression(ExprDefaultGamemode::class.java, GameMode::class.java, ExpressionType.SIMPLE, "[the] default game[ ]mode [of [the] server]")
        }
    }

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parser: SkriptParser.ParseResult): Boolean {
        return true
    }

    override fun get(event: Event): Array<GameMode> {
        return arrayOf(Bukkit.getDefaultGameMode())
    }

    override fun acceptChange(mode: ChangeMode): Array<Class<*>?>? {
        if (mode == ChangeMode.SET || mode == ChangeMode.RESET) return CollectionUtils.array(GameMode::class.java)
        return null
    }

    override fun change(event: Event, delta: Array<out Any?>?, mode: ChangeMode) {
        val change = if (delta != null) delta[0] as GameMode else return
        when (mode) {
            ChangeMode.SET -> Bukkit.setDefaultGameMode(change)
            ChangeMode.RESET -> Bukkit.setDefaultGameMode(GameMode.SURVIVAL)
            else -> {}
        }
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun getReturnType(): Class<out GameMode> {
        return GameMode::class.java
    }

    override fun toString(event: Event?, b: Boolean): String {
        return "default game mode"
    }
}
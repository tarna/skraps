package dev.tarna.skraps.elements.other.expressions

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import org.bukkit.Bukkit
import org.bukkit.event.Event

@Name("Server Port")
@Description("Get the game port that the server runs on.")
@Examples("broadcast \"The server port is %server port%\"")
@Since("1.0.0")
class ExprServerPort : SimpleExpression<Number>() {
    companion object {
        init {
            Skript.registerExpression(ExprServerPort::class.java, Number::class.java, ExpressionType.SIMPLE,
                "[the] server port",
                "[the] port of [the] server"
            )
        }
    }

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parser: SkriptParser.ParseResult): Boolean {
        return true
    }

    override fun get(event: Event): Array<Number?> {
        val port = Bukkit.getPort()
        return arrayOf(port)
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun getReturnType(): Class<out Number> {
        return Number::class.java
    }

    override fun toString(event: Event?, b: Boolean): String {
        return "server port"
    }
}
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

@Name("Server IP")
@Description("Get the IP that this server is bound to, or null if not specified.")
@Examples("broadcast \"The server IP is %server ip%\"")
@Since("1.0.0")
class ExprServerIP : SimpleExpression<String>() {
    companion object {
        init {
            Skript.registerExpression(ExprServerIP::class.java, String::class.java, ExpressionType.SIMPLE,
                "[the] server ip",
                "[the] ip of [the] server"
            )
        }
    }

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parser: SkriptParser.ParseResult): Boolean {
        return true
    }

    override fun get(event: Event): Array<String?> {
        val ip = Bukkit.getIp()
        return arrayOf(ip.ifEmpty { null })
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun getReturnType(): Class<out String> {
        return String::class.java
    }

    override fun toString(event: Event?, b: Boolean): String {
        return "server ip"
    }
}
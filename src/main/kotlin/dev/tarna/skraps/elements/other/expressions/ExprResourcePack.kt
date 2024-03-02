package dev.tarna.skraps.elements.other.expressions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import org.bukkit.Bukkit
import org.bukkit.event.Event

class ExprResourcePack : SimpleExpression<String>() {
    companion object {
        init {
            Skript.registerExpression(ExprResourcePack::class.java, String::class.java, ExpressionType.SIMPLE, "[the] [server] resource[ ]pack ((url|link)|(:hash)|(:prompt))")
        }
    }

    private var type = "url"

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parser: SkriptParser.ParseResult): Boolean {
        if (parser.hasTag("hash")) {
            type = "hash"
        } else if (parser.hasTag("prompt")) {
            type = "prompt"
        }
        return true
    }

    override fun get(event: Event): Array<String?> {
        return arrayOf(when (type) {
                "url" -> Bukkit.getResourcePack().ifEmpty { null }
                "hash" -> Bukkit.getResourcePackHash().ifEmpty { null }
                "prompt" -> Bukkit.getResourcePackPrompt().ifEmpty { null }
                else -> null
            }
        )
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
package dev.tarna.skraps.elements.other.conditions

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Condition
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import org.bukkit.Bukkit
import org.bukkit.event.Event

@Name("Server Is Hardcore")
@Description("Checks whether the server is in hardcore mode or not.")
@Examples("on load:",
    "\tif server is not in hardcore mode:",
    "\t\tbroadcast \"&cDisabling %script% because hardcore mode is required for it to work\"",
    "\t\tdisable skript script"
)
@Since("1.0.0")
class CondServerIsHardcore : Condition() {
    companion object {
        init {
            Skript.registerCondition(CondServerIsHardcore::class.java, "[the] server (is|1¦is(n't| not)) [in] hardcore [mode]")
        }
    }

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: SkriptParser.ParseResult): Boolean {
        isNegated = parseResult.mark == 1
        return true
    }

    override fun check(event: Event): Boolean {
        return Bukkit.isHardcore() != isNegated
    }

    override fun toString(event: Event?, b: Boolean): String {
        return "server is ${if (isNegated) "not " else ""}hardcore"
    }
}
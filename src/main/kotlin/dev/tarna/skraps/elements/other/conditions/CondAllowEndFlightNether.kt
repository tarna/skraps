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

@Name("Allow End, Flight, Nether")
@Description("Whether this server allows the End, Flight, or the Nether.")
@Examples("if server allows the end:", "\tbroadcast \"The server has the end enabled!\"")
@Since("1.0.0")
class CondAllowEndFlightNether : Condition() {
    companion object {
        init {
            Skript.registerCondition(CondAllowEndFlightNether::class.java,
                "[the] [server] [:dis]allow[s] [the] (:end|:flight|:nether)",
                "[the] [server] does(n't| not) allow [the] (:end|:flight|:nether)"
            )
        }
    }

    private var type = ""

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parser: SkriptParser.ParseResult): Boolean {
        if (parser.hasTag("end")) {
            type = "end"
        } else if (parser.hasTag("flight")) {
            type = "flight"
        } else if (parser.hasTag("nether")) {
            type = "nether"
        }
        isNegated = matchedPattern == 1 || parser.hasTag("dis")
        return true
    }

    override fun check(event: Event?): Boolean {
        return if (isNegated)  !getValue() else getValue()
    }

    private fun getValue(): Boolean {
        return when (type) {
            "end" -> Bukkit.getAllowEnd()
            "flight" -> Bukkit.getAllowFlight()
            "nether" -> Bukkit.getAllowNether()
            else -> false
        }
    }

    override fun toString(event: Event?, b: Boolean): String {
        return "server allows the $type"
    }
}
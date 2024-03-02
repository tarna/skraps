package dev.tarna.skraps.elements.other.effects

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import org.bukkit.entity.Player
import org.bukkit.event.Event

@Name("Show Demo Screen")
@Description("Shows the demo screen to the specified player(s).")
@Examples("show demo screen to player")
@Since("1.0.0")
class EffShowDemo : Effect() {
    companion object {
        init {
            if (Skript.methodExists(Player::class.java, "showDemoScreen"))
                Skript.registerEffect(EffShowDemo::class.java, "show demo screen to %players%")
        }
    }

    private lateinit var players: Expression<Player>

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: SkriptParser.ParseResult): Boolean {
        players = expr[0] as Expression<Player>
        return true
    }

    override fun execute(event: Event) {
        val players = players.getArray(event)
        players.forEach { it.showDemoScreen() }
    }

    override fun toString(event: Event?, b: Boolean): String {
        return "show demo screen to ${players.toString(event, b)}"
    }
}
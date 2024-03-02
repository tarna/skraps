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
import org.bukkit.Bukkit
import org.bukkit.event.Event

@Name("Send With Permission")
@Description("Send a message to all players with a specific permission.")
@Examples("send \"Hello!\" to all players with permission \"skraps.example\"")
@Since("1.0.0")
class EffSendWithPermission : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffSendWithPermission::class.java, "send %strings% to [all] players with permission %string%")
        }
    }

    private lateinit var messages: Expression<String>
    private lateinit var permission: Expression<String>

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parser: SkriptParser.ParseResult): Boolean {
        messages = expr[0] as Expression<String>
        permission = expr[1] as Expression<String>
        return true
    }

    override fun execute(pevent: Event) {
        val messages = messages.getArray(pevent) ?: return
        val permission = permission.getSingle(pevent) ?: return

        for (message in messages) {
            Bukkit.broadcast(message, permission)
        }
    }

    override fun toString(event: Event?, b: Boolean): String {
        return "send ${messages.toString(event, b)} to all players with permission ${permission.toString(event, b)}"
    }
}
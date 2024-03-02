package dev.tarna.skraps.elements.other.effects

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.parser.ParserInstance
import ch.njol.skript.log.ErrorQuality
import ch.njol.util.Kleenean
import com.destroystokyo.paper.event.profile.ProfileWhitelistVerifyEvent
import org.bukkit.event.Event

@Name("Whitelist Kick Message")
@Description("the currently planned message to send to the user if they are not whitelisted")
@Examples("on whitelist verify:",
    "\tset whitelist kick message to \"&c&lYou are not whitelisted!\""
)
@Since("1.0.0")
class EffWhitelistKickMessage : Effect() {
    companion object {
        init {
            if (Skript.classExists("com.destroystokyo.paper.event.profile.ProfileWhitelistVerifyEvent"))
                Skript.registerEffect(EffWhitelistKickMessage::class.java, "set white[ ]list kick message to %string%")
        }
    }

    private lateinit var message: Expression<String>

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: SkriptParser.ParseResult): Boolean {
        if (!ParserInstance.get().isCurrentEvent(ProfileWhitelistVerifyEvent::class.java)) {
            Skript.error("The whitelist kick message can only be set in the whitelist verify event.", ErrorQuality.SEMANTIC_ERROR)
            return false
        }
        message = expr[0] as Expression<String>
        return true
    }

    override fun execute(event: Event) {
        val message = message.getSingle(event) ?: return
        (event as ProfileWhitelistVerifyEvent).kickMessage = message
    }

    override fun toString(event: Event?, b: Boolean): String {
        return "set whitelist kick message to ${message.toString(event, b)}"
    }
}
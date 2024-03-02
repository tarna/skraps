package dev.tarna.skraps.elements.datapack.conditions

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

@Name("Datapack Enabled")
@Description("Check if a datapack is enabled or disabled")
@Examples("if data pack \"vanilla\" is enabled:",
    "\tbroadcast \"Vanilla is enabled!\""
)
@Since("1.0.0")
class CondDatapackEnabled : Condition() {
    companion object {
        init {
            Skript.registerCondition(CondDatapackEnabled::class.java,
                "data[ ]pack %string% is (enabled|:disabled)")
        }
    }

    private lateinit var pack: Expression<String>

    private var disabled = false

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parser: SkriptParser.ParseResult): Boolean {
        pack = expr[0] as Expression<String>
        disabled = parser.hasTag("disabled")
        return true
    }

    override fun check(event: Event): Boolean {
        val value = pack.getSingle(event) ?: return false
        val pack = Bukkit.getDatapackManager().packs.find { it.name == value } ?: return false
        return if (disabled) !pack.isEnabled else pack.isEnabled
    }

    override fun toString(event: Event?, b: Boolean): String {
        return "data pack ${pack.toString(event, b)} is ${if (disabled) "disabled" else "enabled"}"
    }
}
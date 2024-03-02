package dev.tarna.skraps.elements.datapack.effects

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import org.bukkit.Bukkit
import org.bukkit.event.Event

class EffEnableDatapack : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffEnableDatapack::class.java, "(enable|:disable) [the] [data]pack %string%")
        }
    }

    private lateinit var pack: Expression<String>

    private var disable = false

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parser: SkriptParser.ParseResult): Boolean {
        pack = expr[0] as Expression<String>
        disable = parser.hasTag("disable")
        return true
    }

    override fun execute(event: Event) {
        val value = pack.getSingle(event) ?: return
        val pack = Bukkit.getDatapackManager().packs.find { it.name == value } ?: return
        pack.isEnabled = !disable
    }

    override fun toString(event: Event?, b: Boolean): String {
        return "${if (disable) "disable" else "enable"} data pack ${pack.toString(event, b)}"
    }
}
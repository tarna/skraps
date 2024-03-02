package dev.tarna.skraps.elements.experimental.effects

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.Crafter
import org.bukkit.event.Event

@Name("Crafter Disabled")
@Description("Enable or disable a slot in a Crafter")
@Examples("disable crafter slot 0 of player's target block")
@Since("1.0.0")
class EffCrafterDisabled : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffCrafterDisabled::class.java,
                "disable [crafter] slot %number% of %block%",
                "enable [crafter] slot %number% of %block%"
            )
        }
    }

    private lateinit var slot: Expression<Number>
    private lateinit var block: Expression<Block>

    private var isDisabled: Boolean = true

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: SkriptParser.ParseResult): Boolean {
        slot = expr[0] as Expression<Number>
        block = expr[1] as Expression<Block>
        isDisabled = matchedPattern == 0
        return true
    }

    override fun execute(event: Event) {
        val slot = slot.getSingle(event) ?: return
        val block = block.getSingle(event) ?: return

        if (block.type != Material.CRAFTER) return
        val crafter = block.state as Crafter
        crafter.setSlotDisabled(slot.toInt(), isDisabled)
    }

    override fun toString(event: Event?, b: Boolean): String {
        return "${if (isDisabled) "disable" else "enable"} crafter slot ${slot.toString(event, b)} of ${block.toString(event, b)}"
    }
}
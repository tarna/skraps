package dev.tarna.skraps.elements.experimental.conditions

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Condition
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.Crafter
import org.bukkit.event.Event

@Name("Crafter Slot Is Disabled")
@Description("Gets whether the slot at the specified index is disabled and will not have items placed in it.")
@Examples("if crafter slot 0 of {_block} is disabled:",
    "\tsend \"The first slot is disabled!\" to player"
)
@Since("1.0.0")
class CondIsCrafterSlotDisabled : Condition() {
    companion object {
        init {
            if (Skript.classExists("org.bukkit.block.Crafter"))
                Skript.registerCondition(CondIsCrafterSlotDisabled::class.java,
                    "[crafter] slot %number% of %block% is disabled",
                    "[crafter] slot %number% of %block% is enabled")
        }
    }

    private lateinit var slot: Expression<Number>
    private lateinit var block: Expression<Block>

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: SkriptParser.ParseResult): Boolean {
        slot = expr[0] as Expression<Number>
        block = expr[1] as Expression<Block>
        isNegated = matchedPattern == 1
        return true
    }

    override fun check(event: Event): Boolean {
        val slot = slot.getSingle(event) ?: return false
        return block.check(event, { block ->
            if (block.type == Material.CRAFTER) {
                (block.state as Crafter).isSlotDisabled(slot.toInt())
            } else false
        }, isNegated)
    }

    override fun toString(event: Event?, b: Boolean): String {
        return "crafter slot ${slot.toString(event, b)} of ${block.toString(event, b)} is ${if (isNegated) "disabled" else "enabled"}"
    }
}
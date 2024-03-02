package dev.tarna.skraps.elements.other.conditions

import ch.njol.skript.Skript
import ch.njol.skript.aliases.ItemType
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Condition
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.util.slot.Slot
import ch.njol.util.Kleenean
import org.bukkit.event.Event

@Name("Slot Can Hold")
@Description("Tests whether an inventory slot can hold the given item.",
    "Similar to the 'Inventory Can Hold' condition, but for slots."
)
@Examples("if slot 0 of player's tool can hold 1 diamond:",
    "\tgive 1 diamond to player",
    "else:",
    "\tsend \"Your first slot is full!\" to player"
)
@Since("1.0.0")
class CondSlotCanHold : Condition() {
    companion object {
        init {
            Skript.registerCondition(CondSlotCanHold::class.java,
                "%slot% (can hold|ha(s|ve) [enough] space (for|to hold)) %itemtype%",
                "%slot% (can(no|')t hold|(ha(s|ve) not|ha(s|ve)n't|do[es]n't have) [enough] space (for|to hold)) %itemtype%"
            )
        }
    }

    private lateinit var slot: Expression<Slot>
    private lateinit var item: Expression<ItemType>

    override fun init(expr: Array<out Expression<*>>, matchedPttern: Int, isDelayed: Kleenean, parseResult: SkriptParser.ParseResult): Boolean {
        slot = expr[0] as Expression<Slot>
        item = expr[1] as Expression<ItemType>
        isNegated = matchedPttern == 1
        return true
    }

    override fun check(event: Event): Boolean {
        val slot = slot.getSingle(event) ?: return false
        val item = item.getSingle(event) ?: return false

        val slotAmt = slot.amount
        val itemAmt = item.amount
        val slotMax = slot.item?.maxStackSize ?: 64

        val result = if (slot.item?.type != item.item?.material) false
        else slotAmt + itemAmt <= slotMax

        return if (isNegated) !result else result
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "${slot.toString(e, debug)} can hold ${item.toString(e, debug)}"
    }
}
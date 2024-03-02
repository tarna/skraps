package dev.tarna.skraps.elements.other.conditions

import ch.njol.skript.conditions.base.PropertyCondition
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import org.bukkit.block.Block
import org.bukkit.block.data.type.Piston

@Name("Piston Is Extended")
@Description("Check if a piston is extended.")
@Examples("if {_piston} is not extended:",
    "\textend piston {_piston}"
)
@Since("1.0.0")
class CondPistonIsExtended : PropertyCondition<Block>() {
    companion object {
        init {
            register(CondPistonIsExtended::class.java, PropertyType.BE, "extended", "blocks")
        }
    }

    override fun check(block: Block): Boolean {
        return if (block.blockData is Piston) {
            (block.blockData as Piston).isExtended
        } else false
    }

    override fun getPropertyName(): String {
        return "extended"
    }
}
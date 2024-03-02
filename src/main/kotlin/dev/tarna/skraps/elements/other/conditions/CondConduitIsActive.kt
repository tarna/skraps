package dev.tarna.skraps.elements.other.conditions

import ch.njol.skript.Skript
import ch.njol.skript.conditions.base.PropertyCondition
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import org.bukkit.block.Block
import org.bukkit.block.Conduit

@Name("Conduit is Active")
@Description("Gets if the conduit is currently active.",
    "Requires the conduit to be placed in the world.",
)
@Examples("if {_conduit} is active:",
    "\tsend \"The conduit is active!\" to player"
)
@Since("1.0.0")
class CondConduitIsActive : PropertyCondition<Block>() {
    companion object {
        init {
            if (Skript.methodExists(Conduit::class.java, "isActive"))
                register(CondConduitIsActive::class.java, "active", "block")
        }
    }

    override fun check(block: Block): Boolean {
        return if (block.state is Conduit) {
            (block.state as Conduit).isActive
        } else false
    }

    override fun getPropertyName(): String {
        return "conduit is active"
    }
}
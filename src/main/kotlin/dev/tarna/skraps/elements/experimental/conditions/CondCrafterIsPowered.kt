package dev.tarna.skraps.elements.experimental.conditions

import ch.njol.skript.Skript
import ch.njol.skript.conditions.base.PropertyCondition
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.Crafter

@Name("Crafter Is Powered")
@Description("Gets whether this Crafter is powered.")
@Examples("if target block is powered:",
    "\tsend \"The Crafter is powered!\" to player"
)
@Since("1.0.0")
class CondCrafterIsPowered : PropertyCondition<Block>() {
    companion object {
        init {
            if (Skript.classExists("org.bukkit.block.Crafter"))
                register(CondCrafterIsPowered::class.java, PropertyType.BE, "powered", "blocks")
        }
    }

    override fun check(block: Block): Boolean {
        return if (block.type == Material.CRAFTER) {
            (block.state as Crafter).isTriggered
        } else false
    }

    override fun getPropertyName(): String {
        return "powered"
    }
}
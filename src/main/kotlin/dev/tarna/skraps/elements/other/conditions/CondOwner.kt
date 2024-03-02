package dev.tarna.skraps.elements.other.conditions

import ch.njol.skript.conditions.base.PropertyCondition
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import dev.tarna.skraps.api.config.config
import org.bukkit.entity.Player

@Name("Owner")
@Description("Check if the player is an owner.",
    "A list of owners can be set in the plugin config.")
@Examples("if player is not an owner:",
    "\tsend \"You cannot do that!\" to player"
)
@Since("1.0.0")
class CondOwner : PropertyCondition<Player>() {
    companion object {
        init {
            register(CondOwner::class.java, "[an|the] owner[s]", "players")
        }
    }

    override fun check(player: Player): Boolean {
        return config.getStringList("settings.owners").contains(player.uniqueId.toString())
    }

    override fun getPropertyName(): String {
        return "owner"
    }
}
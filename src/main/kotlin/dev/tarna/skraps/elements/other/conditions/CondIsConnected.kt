package dev.tarna.skraps.elements.other.conditions

import ch.njol.skript.conditions.base.PropertyCondition
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import org.bukkit.OfflinePlayer

@Name("OfflinePlayer Is Connected")
@Description("Checks whether the connection to this player is still valid.")
@Examples("on join:",
    "\twhile player is connected:",
    "\t\tsend \"Hello World!\" to player",
    "\t\twait 1 minute"
)
@Since("1.0.0")
class CondIsConnected : PropertyCondition<OfflinePlayer>() {
    companion object {
        init {
            register(CondIsConnected::class.java, PropertyType.BE, "connected", "offlineplayers")
        }
    }

    override fun check(player: OfflinePlayer): Boolean {
        return player.isConnected
    }

    override fun getPropertyName(): String {
        return "connected"
    }
}
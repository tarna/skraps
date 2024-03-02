package dev.tarna.skraps.elements.other.events

import ch.njol.skript.Skript
import ch.njol.skript.aliases.ItemType
import ch.njol.skript.lang.util.SimpleEvent
import ch.njol.skript.registrations.EventValues
import ch.njol.skript.util.Getter
import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent
import com.destroystokyo.paper.event.profile.ProfileWhitelistVerifyEvent
import com.destroystokyo.paper.event.server.WhitelistToggleEvent
import com.destroystokyo.paper.loottable.LootableInventoryReplenishEvent
import io.papermc.paper.event.server.WhitelistStateUpdateEvent
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

class PaperEvents : SimpleEvent() {
    companion object {
        init {
            // whitelist events
            if (Skript.classExists("com.destroystokyo.paper.event.server.WhitelistToggleEvent")) {
                Skript.registerEvent("Whitelist Toggle", PaperEvents::class.java, WhitelistToggleEvent::class.java,
                    "white[ ]list (toggle|change)")
                    .description("This event is fired when whitelist is toggled.")
                    .examples("on whitelist toggle:",
                        "\tset {_state} to \"enabled\" if event-boolean is true else \"disabled\"",
                       "\tbroadcast \"whitelist has been %{_state}%\"")
                    .since("1.0.0")

                EventValues.registerEventValue(
                    WhitelistToggleEvent::class.java,
                    Boolean::class.javaObjectType,
                    object : Getter<Boolean, WhitelistToggleEvent>() {
                        override fun get(event: WhitelistToggleEvent): Boolean {
                            return event.isEnabled
                        }
                    }, 0
                )
            }

            if (Skript.classExists("io.papermc.paper.event.server.WhitelistStateUpdateEvent")) {
                Skript.registerEvent("Whitelist Status Update", PaperEvents::class.java, WhitelistStateUpdateEvent::class.java,
                    "white[ ]list state (update|change)")
                    .description("This event gets called when the whitelist status of a player is changed.")
                    .examples("on whitelist state update:",
                        "\tsend \"Your whitelist status has been updated!\" to player")
                    .since("1.0.0")

                EventValues.registerEventValue(
                    WhitelistStateUpdateEvent::class.java,
                    OfflinePlayer::class.java,
                    object : Getter<OfflinePlayer, WhitelistStateUpdateEvent>() {
                        override fun get(event: WhitelistStateUpdateEvent): OfflinePlayer {
                            return event.player
                        }
                    }, 0
                )

                EventValues.registerEventValue(
                    WhitelistStateUpdateEvent::class.java,
                    String::class.java,
                    object : Getter<String, WhitelistStateUpdateEvent>() {
                        override fun get(event: WhitelistStateUpdateEvent): String {
                            return event.status.name
                        }
                    }, 0
                )
            }

            if (Skript.classExists("com.destroystokyo.paper.event.profile.ProfileWhitelistVerifyEvent")) {
                Skript.registerEvent("Whitelist Profile Verify", PaperEvents::class.java, ProfileWhitelistVerifyEvent::class.java,
                    "white[ ]list [profile] verify")
                    .description("Fires when the server needs to verify if a player is whitelisted.",
                        "Plugins may override/control the servers whitelist with this event, and dynamically change the kick message.")
                    .examples("on whitelist profile verify:",
                        "\tset whitelist kick message to \"&cYou are not whitelisted!\"")
                    .since("1.0.0")

                EventValues.registerEventValue(
                    ProfileWhitelistVerifyEvent::class.java,
                    Player::class.java,
                    object : Getter<Player, ProfileWhitelistVerifyEvent>() {
                        override fun get(event: ProfileWhitelistVerifyEvent): Player? {
                            val uuid = event.playerProfile.id ?: return null
                            return Bukkit.getPlayer(uuid)
                        }
                    }, 0
                )
            }

            // elytra events
            if (Skript.classExists("com.destroystokyo.paper.event.player.PlayerElytraBoostEvent")) {
                Skript.registerEvent("Elytra Boost", PaperEvents::class.java, PlayerElytraBoostEvent::class.java,
                    "[player] elytra boost")
                    .description("Fired when a player boosts elytra flight with a firework.")
                    .examples("on player elytra boost:",
                        "\tbroadcast \"%player% has boosted their elytra!\"")
                    .since("1.0.0")

                EventValues.registerEventValue(
                    PlayerElytraBoostEvent::class.java,
                    Player::class.java,
                    object : Getter<Player, PlayerElytraBoostEvent>() {
                        override fun get(event: PlayerElytraBoostEvent): Player {
                            return event.player
                        }
                    }, 0
                )

                EventValues.registerEventValue(
                    PlayerElytraBoostEvent::class.java,
                    ItemType::class.javaObjectType,
                    object : Getter<ItemType, PlayerElytraBoostEvent>() {
                        override fun get(event: PlayerElytraBoostEvent): ItemType {
                            return ItemType(event.itemStack)
                        }
                    }, 0
                )

                EventValues.registerEventValue(
                    PlayerElytraBoostEvent::class.java,
                    Entity::class.java,
                    object : Getter<Entity, PlayerElytraBoostEvent>() {
                        override fun get(event: PlayerElytraBoostEvent): Entity {
                            return event.firework
                        }
                    }, 0
                )
            }

            // loot table events
            if (Skript.classExists("com.destroystokyo.paper.loottable.LootableInventoryReplenishEvent")) {
                Skript.registerEvent("Loot Table Replenish", PaperEvents::class.java, LootableInventoryReplenishEvent::class.java,
                    "loot[ ]table replenish")
                    .description("Fires when a loot table is replenished.")
                    .examples("on loot table replenish:",
                        "\tcancel event",)
                    .since("1.0.0")
            }
        }
    }
}

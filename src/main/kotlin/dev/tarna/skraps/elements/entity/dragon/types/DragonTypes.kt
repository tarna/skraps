package dev.tarna.skraps.elements.entity.dragon.types

import ch.njol.skript.classes.EnumClassInfo
import ch.njol.skript.classes.Parser
import ch.njol.skript.lang.ParseContext
import ch.njol.skript.registrations.Classes
import ch.njol.skript.util.EnumUtils
import org.bukkit.entity.EnderDragon

class DragonTypes {
    companion object {
        init {
            val dragonPhases = EnumUtils(EnderDragon.Phase::class.java, "dragonphase")
            Classes.registerClass(EnumClassInfo(EnderDragon.Phase::class.java, "dragonphase", "dragonphase")
                .user("ender ?dragon ?phases?")
                .name("Dragon Phase")
                .description("Represents a phase or action that an Ender Dragon can perform.")
                .usage(EnderDragon.Phase.entries.toTypedArray().contentToString())
                .since("1.0.0")
                .parser(object : Parser<EnderDragon.Phase>() {
                    override fun parse(input: String, context: ParseContext): EnderDragon.Phase? {
                        return dragonPhases.parse(input)
                    }

                    override fun toVariableNameString(phase: EnderDragon.Phase): String {
                        return phase.name.lowercase().replace("_", " ")
                    }

                    override fun toString(phase: EnderDragon.Phase, flags: Int): String {
                        return toVariableNameString(phase)
                    }
                })
            )
        }
    }
}
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

@Name("Crafter Powered")
@Description("Sets whether this Crafter is powered.")
@Examples("set powered of crafter {_block} to true")
@Since("1.0.0")
class EffCrafterPowered : Effect() {
    companion object {
        init {
            if (Skript.classExists("org.bukkit.block.Crafter"))
                Skript.registerEffect(EffCrafterPowered::class.java, "set powered of [crafter] %block% to %boolean%")
        }
    }

    private lateinit var block: Expression<Block>
    private lateinit var state: Expression<Boolean>

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: SkriptParser.ParseResult): Boolean {
        block = expr[0] as Expression<Block>
        state = expr[1] as Expression<Boolean>
        return true
    }

    override fun execute(event: Event) {
        val block = block.getSingle(event) ?: return
        val state = state.getSingle(event) ?: return

        if (block.type == Material.CRAFTER) {
            (block.state as Crafter).isTriggered = state
        }
    }

    override fun toString(event: Event?, b: Boolean): String {
        return "set powered of ${block.toString(event, b)} to ${state.toString(event, b)}"
    }
}
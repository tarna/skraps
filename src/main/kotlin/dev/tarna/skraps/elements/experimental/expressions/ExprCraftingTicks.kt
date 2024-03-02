package dev.tarna.skraps.elements.experimental.expressions

import ch.njol.skript.Skript
import ch.njol.skript.classes.Changer.ChangeMode
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import ch.njol.util.coll.CollectionUtils
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.Crafter
import org.bukkit.event.Event

@Name("Crafting Ticks")
@Description("The number of ticks which this block will remain in the crafting state for.")
@Examples("set crafting ticks of {_crafter} to 100")
@Since("1.0.0")
class ExprCraftingTicks : SimpleExpression<Number>() {
    companion object {
        init {
            Skript.registerExpression(ExprCraftingTicks::class.java, Number::class.java, ExpressionType.SIMPLE,
                "crafting ticks of %block%"
            )
        }
    }

    private lateinit var block: Expression<Block>

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: SkriptParser.ParseResult): Boolean {
        block = expr[0] as Expression<Block>
        return true
    }

    override fun get(event: Event): Array<Number?> {
        val block = block.getSingle(event) ?: return emptyArray()
        val ticks = if (block.type == Material.CRAFTER) {
            (block.state as Crafter).craftingTicks
        } else null
        return arrayOf(ticks)
    }

    override fun acceptChange(mode: ChangeMode): Array<Class<*>>? {
        return when (mode) {
            ChangeMode.ADD, ChangeMode.SET, ChangeMode.REMOVE, ChangeMode.RESET -> CollectionUtils.array(Number::class.java)
            else -> null
        }
    }

    override fun change(event: Event, delta: Array<out Any?>?, mode: ChangeMode) {
        val block = block.getSingle(event) ?: return
        if (block.type != Material.CRAFTER) return

        val change = if ((delta != null && delta[0] is Number)) (delta[0] as Number).toInt() else 0
        when (mode) {
            ChangeMode.ADD -> (block.state as Crafter).craftingTicks += change
            ChangeMode.SET -> (block.state as Crafter).craftingTicks = change
            ChangeMode.REMOVE -> (block.state as Crafter).craftingTicks -= change
            ChangeMode.RESET -> (block.state as Crafter).craftingTicks = 0
            else -> {}
        }
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun getReturnType(): Class<out Number> {
        return Number::class.java
    }

    override fun toString(event: Event?, b: Boolean): String {
        return "crafting ticks of ${block.toString(event, b)}"
    }
}
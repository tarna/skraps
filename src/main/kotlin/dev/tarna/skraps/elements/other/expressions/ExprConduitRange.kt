package dev.tarna.skraps.elements.other.expressions

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.expressions.base.PropertyExpression
import ch.njol.skript.expressions.base.SimplePropertyExpression
import org.bukkit.block.Block
import org.bukkit.block.Conduit

@Name("Conduit Range")
@Description("Gets the range in which the Conduit Power effect gets added to players.",
    "Requires the conduit to be placed in the world.",
    "The range is 16 blocks for every 7 blocks placed in the frame with a max of 42 blocks with a range of 96."
)
@Examples("if (distance between player and {_conduit}) >= range of {_conduit}:",
    "\tsend \"You are out of range of the conduit!\" to player"
)
@Since("1.0.0")
class ExprConduitRange : SimplePropertyExpression<Block, Number>() {
    companion object {
        init {
            if (Skript.methodExists(Conduit::class.java, "getRange"))
                PropertyExpression.register(ExprConduitRange::class.java, Number::class.java, "range", "blocks")
        }
    }

    override fun convert(block: Block): Number? {
        return if (block.state is Conduit) {
            (block.state as Conduit).range
        } else null
    }

    override fun getReturnType(): Class<out Number> {
        return Number::class.java
    }

    override fun getPropertyName(): String {
        return "range"
    }
}
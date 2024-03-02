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
import org.bukkit.entity.LivingEntity

@Name("Conduit Target")
@Description("Gets the current target of the conduit.",
    "This is the entity the conduit is attacking when it is maxed out."
)
@Examples("if conduit target of {_conduit} is player:",
    "\tsend \"You are being attacked by a conduit!\" to player"
)
@Since("1.0.0")
class ExprConduitTarget : SimplePropertyExpression<Block, LivingEntity>() {
    companion object {
        init {
            if (Skript.methodExists(Conduit::class.java, "getTarget"))
                PropertyExpression.register(ExprConduitTarget::class.java, LivingEntity::class.java, "conduit target", "block")
        }
    }

    override fun convert(block: Block): LivingEntity? {
        return if (block.state is Conduit) {
            (block.state as Conduit).target
        } else null
    }

    override fun getReturnType(): Class<out LivingEntity> {
        return LivingEntity::class.java
    }

    override fun getPropertyName(): String {
        return "target"
    }
}
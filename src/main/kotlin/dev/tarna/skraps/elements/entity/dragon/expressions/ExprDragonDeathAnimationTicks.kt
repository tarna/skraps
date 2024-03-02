package dev.tarna.skraps.elements.entity.dragon.expressions

import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.expressions.base.SimplePropertyExpression
import dev.tarna.skraps.elements.entity.bee.expressions.ExprBeeCannotEnterHiveTicks
import org.bukkit.entity.EnderDragon
import org.bukkit.entity.LivingEntity

@Name("Dragon Death Animation Ticks")
@Description("Get the current time in ticks relative to the start of this dragon's death animation.")
@Examples("broadcast \"The dragon has been dying for %dragon death animation ticks of the {_dragon}% ticks!\"")
@Since("1.0.0")
class ExprDragonDeathAnimationTicks : SimplePropertyExpression<LivingEntity, Number>() {
    companion object {
        init {
            register(ExprBeeCannotEnterHiveTicks::class.java, Number::class.java, "[the] [dragon] death animation ticks", "livingentities")
        }
    }

    override fun convert(entity: LivingEntity): Number? {
        return if (entity is EnderDragon) entity.deathAnimationTicks else null
    }

    override fun getReturnType(): Class<out Number> {
        return Number::class.java
    }

    override fun getPropertyName(): String {
        return "dragon death animation ticks"
    }
}
package dev.tarna.skraps.elements.entity.goat.conditions

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Condition
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import org.bukkit.entity.Goat
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event

@Name("Goat Has Horn")
@Description("Check if a goat has a left or right horn.")
@Examples("{_goat} has a left horn",
    "{_goat} has a right horn"
)
@Since("1.0.0")
class CondGoatHasHorn : Condition() {
    companion object {
        init {
            Skript.registerCondition(CondGoatHasHorn::class.java, "%livingentities% has [a] (left|:right) horn")
        }
    }

    private lateinit var entity: Expression<LivingEntity>

    private var isRight = false

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parser: SkriptParser.ParseResult): Boolean {
        entity = expr[0] as Expression<LivingEntity>
        isRight = parser.hasTag("right")
        return true
    }

    override fun check(event: Event): Boolean {
        val entity = entity.getSingle(event) ?: return false
        if (entity !is Goat) return false
        return if (isRight) entity.hasRightHorn() else entity.hasLeftHorn()
    }

    override fun toString(event: Event?, b: Boolean): String {
        return "${entity.toString(event, b)} has ${if (isRight) "right" else "left"} horn"
    }
}
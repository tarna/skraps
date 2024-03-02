package dev.tarna.skraps.elements.entity.goat.effects

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import dev.tarna.skraps.elements.entity.goat.conditions.CondGoatHasHorn
import org.bukkit.entity.Goat
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event

@Name("Goat Horn")
@Description("Sets if this goat has its left or right horn.")
@Examples("set left horn of {_goat} to true",
    "set right horn of {_goat} to false"
)
@Since("1.0.0")
class EffGoatHorn : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffGoatHorn::class.java, "set (left|:right) horn of %livingentities% to %boolean%")
        }
    }

    private lateinit var entities: Expression<LivingEntity>
    private lateinit var value: Expression<Boolean>

    private var isRight = false

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parser: SkriptParser.ParseResult): Boolean {
        entities = expr[0] as Expression<LivingEntity>
        value = expr[1] as Expression<Boolean>
        isRight = parser.hasTag("right")
        return true
    }

    override fun execute(event: Event) {
        val entities = entities.getArray(event)
        val value = value.getSingle(event) ?: return

        for (entity in entities) {
            if (entity !is Goat) continue
            if (isRight) entity.setRightHorn(value) else entity.setLeftHorn(value)
        }
    }

    override fun toString(event: Event?, b: Boolean): String {
        return "set ${if (isRight) "right" else "left"} horn of ${entities.toString(event, b)} to ${value.toString(event, b)}"
    }
}
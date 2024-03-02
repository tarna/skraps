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
import org.bukkit.entity.Goat
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event

@Name("Goat Ram")
@Description("Makes the goat ram at the specified entity.")
@Examples("make {_goat} ram into player")
@Since("1.0.0")
class EffGoatRam : Effect() {
    companion object {
        init {
            Skript.registerEffect(EffGoatRam::class.java, "make %livingentities% ram [into] %livingentity%")
        }
    }

    private lateinit var entities: Expression<LivingEntity>
    private lateinit var target: Expression<LivingEntity>

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: SkriptParser.ParseResult): Boolean {
        entities = expr[0] as Expression<LivingEntity>
        target = expr[1] as Expression<LivingEntity>
        return true
    }

    override fun execute(event: Event) {
        val entities = entities.getArray(event)
        val target = target.getSingle(event) ?: return
        for (entity in entities) {
            if (entity !is Goat) continue
            if (entity == target) continue

            entity.ram(target)
        }
    }

    override fun toString(event: Event?, b: Boolean): String {
        return "make ${entities.toString(event, b)} ram into ${target.toString(event, b)}"
    }
}
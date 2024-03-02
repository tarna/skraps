package dev.tarna.skraps.elements.other.expressions

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
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.World
import org.bukkit.entity.SpawnCategory
import org.bukkit.event.Event

@Name("Spawn Limit")
@Description("The limit for number of mobs that can spawn in a chunk.",
    "If a world is specified, it will return the spawn limit for that world.",
    "The different categories are: ambient, animal, axolotl, monster, underground, water ambient, water animal, and underground."
)
@Examples("set {_} to animal spawn limit of world(\"world\")",
    "if {_} >= 10:",
    "\tset the animal spawn limit of world(\"world\") to 25"
)
@Since("1.0.0")
class ExprSpawnLimit : SimpleExpression<Number>() {
    companion object {
        init {
            Skript.registerExpression(ExprSpawnLimit::class.java, Number::class.java, ExpressionType.SIMPLE,
                "[the] [:water] (:ambient|:animal|:axolotl|:monster|:underground) spawn limit [of [the] %-world%]"
            )
        }
    }

    private var world: Expression<World>? = null

    private var category = SpawnCategory.ANIMAL

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parser: SkriptParser.ParseResult): Boolean {
        world = expr[0] as Expression<World>?
        when {
            parser.hasTag("water") && parser.hasTag("ambient") -> category = SpawnCategory.WATER_AMBIENT
            parser.hasTag("water") && parser.hasTag("animal") -> category = SpawnCategory.WATER_ANIMAL
            parser.hasTag("ambient") -> category = SpawnCategory.AMBIENT
            parser.hasTag("animal") -> category = SpawnCategory.ANIMAL
            parser.hasTag("axolotl") -> category = SpawnCategory.AXOLOTL
            parser.hasTag("monster") -> category = SpawnCategory.MONSTER
            parser.hasTag("underground") -> category = SpawnCategory.WATER_UNDERGROUND_CREATURE
        }
        return true
    }

    override fun get(event: Event): Array<Number> {
        val world = world?.getSingle(event)
        return if (world != null) {
            arrayOf(world.getSpawnLimit(category))
        } else {
            arrayOf(Bukkit.getSpawnLimit(category))
        }
    }

    override fun acceptChange(mode: ChangeMode): Array<Class<*>?>? {
        if (mode == ChangeMode.SET) return CollectionUtils.array(GameMode::class.javaObjectType)
        return null
    }

    override fun change(event: Event, delta: Array<out Any?>?, mode: ChangeMode) {
        val change = if (delta != null) delta[0] as Number else return
        val world = world?.getSingle(event)
        when (mode) {
            ChangeMode.SET -> {
                world?.setSpawnLimit(category, change.toInt())
            }
            ChangeMode.ADD -> {
                world?.setSpawnLimit(category, world.getSpawnLimit(category) + change.toInt())
            }
            ChangeMode.REMOVE -> {
                world?.setSpawnLimit(category, world.getSpawnLimit(category) - change.toInt())
            }
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
        return "default game mode"
    }
}
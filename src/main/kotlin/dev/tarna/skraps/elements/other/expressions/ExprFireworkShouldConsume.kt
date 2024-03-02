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
import ch.njol.skript.lang.parser.ParserInstance
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import ch.njol.util.coll.CollectionUtils
import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent
import org.bukkit.event.Event

@Name("Should Consume Firework")
@Description("Whether to consume the firework or not in the elytra boost event.")
@Examples("on elytra boost:",
    "\tset should consume firework to false",
)
@Since("1.0.0")
class ExprFireworkShouldConsume : SimpleExpression<Boolean>() {
    companion object {
        init {
            Skript.registerExpression(ExprFireworkShouldConsume::class.java, Boolean::class.javaObjectType, ExpressionType.SIMPLE,
                "should consume firework"
            )
        }
    }

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: SkriptParser.ParseResult): Boolean {
        if (!ParserInstance.get().isCurrentEvent(PlayerElytraBoostEvent::class.java)) {
            Skript.error("The expression 'should consume firework' can only be used in the elytra boost event.")
            return false
        }
        return true
    }

    override fun get(event: Event): Array<Boolean>? {
        return if (event is PlayerElytraBoostEvent) arrayOf(event.shouldConsume()) else null
    }

    override fun acceptChange(mode: ChangeMode): Array<Class<*>>? {
        return when (mode) {
            ChangeMode.SET -> CollectionUtils.array(Boolean::class.javaObjectType)
            else -> null
        }
    }

    override fun change(event: Event, delta: Array<out Any?>?, mode: ChangeMode) {
        val shouldConsume = if (delta != null && delta[0] is Boolean) delta[0] as Boolean else false
        if (mode == ChangeMode.SET) (event as PlayerElytraBoostEvent).setShouldConsume(shouldConsume)
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun getReturnType(): Class<out Boolean> {
        return Boolean::class.javaObjectType
    }

    override fun toString(event: Event?, b: Boolean): String {
        return "should consume firework"
    }
}
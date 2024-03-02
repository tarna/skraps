package dev.tarna.skraps.elements.entity.slime.expressions

import ch.njol.skript.Skript
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.parser.ParserInstance
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import com.destroystokyo.paper.event.entity.SlimeChangeDirectionEvent
import org.bukkit.event.Event

class ExprSlimeNewYaw : SimpleExpression<Number>() {
    companion object {
        init {
            Skript.registerExpression(ExprSlimeNewYaw::class.java, Number::class.java, ExpressionType.EVENT, "[slime] new yaw")
        }
    }

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parser: SkriptParser.ParseResult): Boolean {
        if (!ParserInstance.get().isCurrentEvent(SlimeChangeDirectionEvent::class.java)) {
            Skript.error("The expression 'slime new yaw' can only be used in the 'slime change direction' event.")
            return false
        }
        return true
    }

    override fun get(event: Event): Array<Number?> {
        return arrayOf((event as SlimeChangeDirectionEvent).newYaw)
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun getReturnType(): Class<out Number> {
        return Number::class.java
    }

    override fun toString(event: Event?, b: Boolean): String {
        return "slime new yaw"
    }
}
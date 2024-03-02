package dev.tarna.skraps.api.util

import ch.njol.skript.Skript
import ch.njol.skript.lang.ExpressionInfo
import java.util.concurrent.atomic.AtomicInteger



object SkriptUtils {
    fun getElementCount(): IntArray {
        val i = IntArray(5)

        i[0] = Skript.getEvents().size
        i[1] = Skript.getEffects().size
        val exprs = AtomicInteger()
        Skript.getExpressions().forEachRemaining { _: ExpressionInfo<*, *>? -> exprs.getAndIncrement() }
        i[2] = exprs.get()
        i[3] = Skript.getConditions().size
        i[4] = Skript.getSections().size

        return i
    }
}
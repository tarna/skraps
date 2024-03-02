package dev.tarna.skraps.elements.datapack.expressions

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import org.bukkit.Bukkit
import org.bukkit.event.Event

@Name("Datapacks")
@Description("Get the datapacks on the server")
@Examples("send \"%the enabled datapacks%\" to console")
@Since("1.0.0")
class ExprDatapacks : SimpleExpression<String>() {
    companion object {
        init {
            Skript.registerExpression(
                ExprDatapacks::class.java, String::class.java, ExpressionType.SIMPLE,
                "[the] [:initial] (:disabled|enabled) [data]packs"
            )
        }
    }

    private var initial = false
    private var disabled = false

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parser: SkriptParser.ParseResult): Boolean {
        initial = parser.hasTag("initial")
        disabled = parser.hasTag("disabled")
        return true
    }

    override fun get(event: Event): Array<String> {
        if (initial) {
            return if (disabled) {
                Bukkit.getInitialDisabledPacks().toTypedArray()
            } else {
                Bukkit.getInitialEnabledPacks().toTypedArray()
            }
        } else {
            return if (disabled) {
                Bukkit.getDatapackManager().packs.filter { !it.isEnabled }.map { it.name }.toTypedArray()
            } else {
                Bukkit.getDatapackManager().enabledPacks.map { it.name }.toTypedArray()
            }
        }
    }

    override fun isSingle(): Boolean {
        return false
    }

    override fun getReturnType(): Class<out String> {
        return String::class.java
    }

    override fun toString(event: Event?, b: Boolean): String {
        TODO("Not yet implemented")
    }
}
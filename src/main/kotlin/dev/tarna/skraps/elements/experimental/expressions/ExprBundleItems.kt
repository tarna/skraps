package dev.tarna.skraps.elements.experimental.expressions

import ch.njol.skript.Skript
import ch.njol.skript.aliases.ItemType
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
import org.bukkit.event.Event
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BundleMeta

@Name("Bundle Items")
@Description("The items in a bundle")
@Examples("set {_items::*} to items in bundle player's tool")
@Since("1.0.0")
class ExprBundleItems : SimpleExpression<ItemType>() {
    companion object {
        init {
            Skript.registerExpression(ExprBundleItems::class.java, ItemType::class.java, ExpressionType.SIMPLE,
                "items (of|in) %itemtype%"
            )
        }
    }

    private lateinit var item: Expression<ItemType>

    override fun init(expr: Array<out Expression<*>>, matchedPattern: Int, isDelayed: Kleenean, parseResult: SkriptParser.ParseResult): Boolean {
        item = expr[0] as Expression<ItemType>
        return true
    }

    override fun get(event: Event): Array<ItemType?> {
        val item = item.getSingle(event) ?: return emptyArray()
        if (item.itemMeta !is BundleMeta) return emptyArray()

        val meta = item.itemMeta as BundleMeta
        return meta.items.map { ItemType(it) }.toTypedArray()
    }

    override fun acceptChange(mode: ChangeMode): Array<Class<*>>? {
        return CollectionUtils.array(ItemType::class.java)
    }

    override fun change(event: Event, delta: Array<out Any?>?, mode: ChangeMode) {
        val item = item.getSingle(event) ?: return
        if (item.itemMeta !is BundleMeta) return

        val meta = item.itemMeta as BundleMeta
        val change = if (delta != null) delta as Array<ItemType> else return

        when (mode) {
            ChangeMode.ADD -> change.forEach { meta.addItem(ItemStack(it.material)) }
            ChangeMode.SET -> meta.setItems(change.map { ItemStack(it.material) })
            ChangeMode.REMOVE, ChangeMode.DELETE -> meta.setItems(meta.items.filter { item -> change.none { it.material == item.type } })
            ChangeMode.RESET, ChangeMode.REMOVE_ALL -> {
                val items = meta.items
                items.clear()
                meta.setItems(items)
            }
            else -> return
        }

        item.itemMeta = meta
    }

    override fun isSingle(): Boolean {
        return false
    }

    override fun getReturnType(): Class<out ItemType> {
        return ItemType::class.java
    }

    override fun toString(event: Event?, b: Boolean): String {
        return "items"
    }
}
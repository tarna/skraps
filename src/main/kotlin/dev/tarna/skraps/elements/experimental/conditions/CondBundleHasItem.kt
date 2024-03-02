package dev.tarna.skraps.elements.experimental.conditions

import ch.njol.skript.aliases.ItemType
import ch.njol.skript.conditions.base.PropertyCondition
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import org.bukkit.inventory.meta.BundleMeta

@Name("Bundle Has Item")
@Description("Check if a bundle has an item")
@Examples("if items of player's tool has 1 diamond:",
    "\tremove 1 diamond from items of player's tool"
)
@Since("1.0.0")
class CondBundleHasItem : PropertyCondition<ItemType>() {
    companion object {
        init {
            register(CondBundleHasItem::class.java, PropertyType.HAVE, "items", "itemtypes")
        }
    }

    override fun check(item: ItemType): Boolean {
        return if (item.itemMeta is BundleMeta) {
            (item.itemMeta as BundleMeta).hasItems()
        } else false
    }

    override fun getPropertyName(): String {
        return "bundle has item"
    }
}
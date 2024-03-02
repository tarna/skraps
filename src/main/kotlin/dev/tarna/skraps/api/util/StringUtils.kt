package dev.tarna.skraps.api.util

import net.kyori.adventure.text.Component

fun String.color(): Component {
    return mm.deserialize(this)
}

fun String.component(): Component {
    return Component.text(this)
}
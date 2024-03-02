package dev.tarna.skraps.api.util

fun ticksToMs(ticks: Long?): Long {
    if (ticks == null) return -1
    return ticks * 50
}
package com.levnovikov.system_endless_scroll

interface PositionProvider {
    fun getLastVisibleItemPosition(): Int
}
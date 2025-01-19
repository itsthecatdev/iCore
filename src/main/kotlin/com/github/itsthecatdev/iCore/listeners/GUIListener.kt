package com.github.itsthecatdev.iCore.listeners

import com.github.itsthecatdev.iCore.gui.GUIManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

class GUIListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        GUIManager.handleClick(event)
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        GUIManager.removeGUI(event.inventory)
    }
}
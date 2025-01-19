package com.github.itsthecatdev.iCore.gui

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory

object GUIManager {
    private val openGUIs = mutableMapOf<Inventory, InventoryGUI>()

    fun openGUI(player: Player, gui: InventoryGUI) {
        gui.openInventory(player)
        openGUIs[player.openInventory.topInventory] = gui
    }

    fun handleClick(event: InventoryClickEvent) {
        val gui = openGUIs[event.inventory] ?: return
        gui.handleClick(event)
    }

    fun removeGUI(inventory: Inventory) {
        openGUIs.remove(inventory)
    }
}
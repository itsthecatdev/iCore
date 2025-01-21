package com.github.itsthecatdev.iCore.gui

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

abstract class InventoryGUI(private val title: String, private val size: Int) {
    protected val inventory: Inventory = Bukkit.createInventory(null, size, "${ChatColor.WHITE}$title")

    abstract fun initializeItems(player: Player)

    abstract fun handleClick(event: InventoryClickEvent)

    fun openInventory(player: Player) {
        initializeItems(player)
        player.openInventory(inventory)
    }

    protected fun setItem(slot: Int, item: ItemStack) {
        inventory.setItem(slot, item)
    }
}
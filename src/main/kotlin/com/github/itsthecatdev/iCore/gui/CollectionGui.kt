package com.github.itsthecatdev.iCore.gui

import com.github.itsthecatdev.iCore.ICore
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.time.Instant
import java.time.temporal.ChronoUnit

class CollectionGui : InventoryGUI("\uE104ʩ", 54)  {
    companion object {
        private const val REWARD_SLOT = 13
        private const val COOLDOWN_HOURS = 24
    }

    override fun initializeItems(player: Player) {
        val close = ItemStack(Material.IRON_NUGGET).apply {
            itemMeta = itemMeta?.apply {
                setCustomModelData(5)
                setHideTooltip(true)
            }
        }


        setItem(0, close)
    }

    override fun handleClick(event: InventoryClickEvent) {
        val playerInventory = event.whoClicked.inventory;
        if (event.rawSlot >= 38 && event.rawSlot <= 42) {
            val currentItem = event.currentItem
            if (currentItem != null && currentItem.type != Material.AIR) {
                val scrapOutPut = ItemStack(Material.PAPER).apply {
                    itemMeta = itemMeta?.apply {
                        displayName(Component.text("Scrap").color(NamedTextColor.WHITE))
                        setCustomModelData(15003)
                    }
                }
                setItem(24, scrapOutPut)
                setItem(20, ItemStack(Material.AIR))
            }

            event.isCancelled = true
        }
        if (event.rawSlot ==0) {
            event.whoClicked.closeInventory()
        } else {
            event.isCancelled = true
        }
        }


    private fun giveReward(player: Player) {
        val reward = ItemStack(Material.DIAMOND, 5)
        player.inventory.addItem(reward)
        player.sendMessage("§aYou've claimed your daily reward!")
        setLastClaimTime(player, Instant.now())
    }

    private fun getLastClaimTime(player: Player): Instant? {
        return ICore.instance.config.getString("lastClaimTimes.${player.uniqueId}")?.let { Instant.parse(it) }
    }

    private fun setLastClaimTime(player: Player, time: Instant) {
        ICore.instance.config.set("lastClaimTimes.${player.uniqueId}", time.toString())
        ICore.instance.saveConfig()
    }
}
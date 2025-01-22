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

class ScrapGui : InventoryGUI("\uE104ʤ", 54)  {
    companion object {
        private const val REWARD_SLOT = 13
        private const val COOLDOWN_HOURS = 24
    }

    override fun initializeItems(player: Player) {
        val canPress = canScrap(player)
        val item = if (canPress) {
            ItemStack(Material.IRON_NUGGET).apply {
                itemMeta = itemMeta?.apply {
                    setCustomModelData(5)
                    itemName(Component.text("Scrap Item").color(NamedTextColor.WHITE) )
                    lore(
                        listOf(
                            Component.empty(),
                            Component.text("Scraping Items Gives you Some Scrap,")
                                .color(TextColor.color(230,255,255))
                                .decoration(TextDecoration.ITALIC,false),
                            Component.text("but will in return delete the item!")
                                .color(TextColor.color(255,120,120))
                                .decoration(TextDecoration.ITALIC,false)
                        )
                    )
                }
            }
        } else {
            ItemStack(Material.PAPER).apply {
                itemMeta = itemMeta?.apply {
                    itemName(Component.text { "You've already claimed your reward" } )
                    lore(listOf(Component.text("Come back tomorrow!")))
                    setCustomModelData(17003)
                }
            }
        }


        setItem(38, item)
        setItem(39, item)
        setItem(40, item)
        setItem(41, item)
        setItem(42, item)
    }

    override fun handleClick(event: InventoryClickEvent) {
        val playerInventory = event.whoClicked.inventory
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
        if (event.rawSlot == 24) {
            event.isCancelled = false
        } else {
            event.isCancelled = true
        }
        if (event.rawSlot == 20) {
            event.isCancelled = false
        }
        if (event.clickedInventory == playerInventory)
        {
            event.isCancelled = false
        }
        if (event.clickedInventory == playerInventory && event.isShiftClick)
        {
            event.isCancelled = true
        }
        }

    private fun canScrap(player: Player): Boolean {
        return true
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
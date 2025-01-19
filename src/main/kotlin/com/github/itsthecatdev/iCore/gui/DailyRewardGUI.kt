package com.github.itsthecatdev.iCore.gui

import com.github.itsthecatdev.iCore.ICore
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.time.Instant
import java.time.temporal.ChronoUnit

class DailyRewardGUI : InventoryGUI("Daily Reward", 27) {
    companion object {
        private const val REWARD_SLOT = 13
        private const val COOLDOWN_HOURS = 24
    }

    override fun initializeItems(player: Player) {
        val canClaim = canClaimReward(player)
        val item = if (canClaim) {
            ItemStack(Material.DIAMOND).apply {
                itemMeta = itemMeta?.apply {
                    itemName(Component.text { "Click to claim your daily reward!" } )
                }
            }
        } else {
            ItemStack(Material.BARRIER).apply {
                itemMeta = itemMeta?.apply {
                    itemName(Component.text { "You've already claimed your reward" } )
                    lore(listOf(Component.text("Come back tomorrow!")))
                }
            }
        }
        setItem(REWARD_SLOT, item)
    }

    override fun handleClick(event: InventoryClickEvent) {
        event.isCancelled = true
        if (event.rawSlot == REWARD_SLOT) {
            val player = event.whoClicked as Player
            if (canClaimReward(player)) {
                giveReward(player)
                player.closeInventory()
            }
        }
    }

    private fun canClaimReward(player: Player): Boolean {
        val lastClaimTime = getLastClaimTime(player)
        return lastClaimTime == null || ChronoUnit.HOURS.between(lastClaimTime, Instant.now()) >= COOLDOWN_HOURS
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
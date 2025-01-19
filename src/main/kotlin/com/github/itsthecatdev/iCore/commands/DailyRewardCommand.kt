package com.github.itsthecatdev.iCore.commands

import com.github.itsthecatdev.iCore.gui.DailyRewardGUI
import com.github.itsthecatdev.iCore.gui.GUIManager
import dev.jorel.commandapi.kotlindsl.commandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor

object DailyRewardCommand {
    fun register() {
        commandAPICommand("daily") {
            withAliases("dailyreward", "dailyrewards")
            playerExecutor { player, _ ->
                GUIManager.openGUI(player, DailyRewardGUI())
            }
        }
    }
}
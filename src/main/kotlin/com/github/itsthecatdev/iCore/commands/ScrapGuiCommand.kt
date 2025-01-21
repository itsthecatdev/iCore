package com.github.itsthecatdev.iCore.commands

import com.github.itsthecatdev.iCore.gui.DailyRewardGUI
import com.github.itsthecatdev.iCore.gui.GUIManager
import com.github.itsthecatdev.iCore.gui.ScrapGui
import dev.jorel.commandapi.kotlindsl.commandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor

object ScrapGuiCommand {
    fun register() {
        commandAPICommand("scrap") {
            playerExecutor { player, _ ->
                GUIManager.openGUI(player, ScrapGui())
            }
        }
    }
}

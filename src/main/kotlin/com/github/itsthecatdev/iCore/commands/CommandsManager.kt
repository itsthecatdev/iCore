package com.github.itsthecatdev.iCore.commands

object CommandsManager {

    fun loadCommands() {
        DailyRewardCommand.register()
        ScrapGuiCommand.register()
        CollectionGuiCommand.register()

    }
}
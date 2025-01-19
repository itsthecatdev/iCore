package com.github.itsthecatdev.iCore

import com.github.itsthecatdev.iCore.commands.CommandsManager
import com.github.itsthecatdev.iCore.listeners.GUIListener
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import org.bukkit.plugin.java.JavaPlugin

class ICore : JavaPlugin() {

    companion object {
        lateinit var instance: ICore
            private set
    }

    override fun onLoad() {
        instance = this
        CommandAPI.onLoad(CommandAPIBukkitConfig(this).verboseOutput(true))
    }

    override fun onEnable() {
        CommandAPI.onEnable()
        CommandsManager.loadCommands()
        server.pluginManager.registerEvents(GUIListener(), this)
    }

    override fun onDisable() {
        CommandAPI.onDisable()
    }
}